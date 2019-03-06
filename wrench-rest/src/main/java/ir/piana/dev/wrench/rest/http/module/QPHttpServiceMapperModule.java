package ir.piana.dev.wrench.rest.http.module;

import ir.piana.dev.wrench.core.QPException;
import ir.piana.dev.wrench.core.module.QPBaseModule;
import ir.piana.dev.wrench.rest.authenticate.AuthenticateService;
import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;
import ir.piana.dev.wrench.rest.authenticate.repo.PrincipalRepository;
import ir.piana.dev.wrench.rest.authorize.QPRoleProvider;
import ir.piana.dev.wrench.rest.authorize.QPRoleManagerModule;
import ir.piana.dev.wrench.rest.authorize.QPRoleProvidable;
import ir.piana.dev.wrench.rest.http.core.*;
import ir.piana.dev.wrench.rest.http.construct.QPHandlerConstruct;
import ir.piana.dev.wrench.rest.http.construct.QPRepositoryConstruct;
import ir.piana.dev.wrench.rest.http.util.QPHttpInjectableConfigImpl;
import org.jdom2.Element;
import org.jpos.q2.QBean;
import org.jpos.space.SpaceListener;
import org.jpos.transaction.Context;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class QPHttpServiceMapperModule
        extends QPBaseModule
        implements SpaceListener<String, Context> {
    private Map<String, Class> repoMap = new LinkedHashMap<>();
    private Map<String, QPHttpRepositoryManager> repositoryManagerMap
            = new LinkedHashMap<>();
    private Map<String, QPHandlerConstruct> handlerConstructMap =
            new LinkedHashMap<>();
    protected Map<String, QPHandlerConstruct> httpAsteriskHandlerConstructMap =
            new LinkedHashMap<>();

//    protected QPHttpAuthenticator authenticator;
    protected String authenticatorModuleName;
    protected String authorizerModuleName;

    protected ExecutorService listener;
    protected ExecutorService worker;

    protected String springContextName;



    @Override
    protected void configBeforeRegisterQPModule() throws Exception {
    }

    @Override
    protected void initBeforeRegisterQPModule() throws Exception {
    }

    @Override
    protected void configForSpringContext() throws Exception {
        getSpringContext()
                .register(AuthenticateService.class);
    }

    @Override
    protected void configAfterRegisterQPModule() throws Exception {
        springContextName = getPersist().getChildText("qp-spring-context");
        springContextName = springContextName == null ?
                "default" : springContextName;
        Element authenticatorElement = getPersist()
                .getChild("qp-authenticator");
        if (authenticatorElement != null)
            authenticatorModuleName = authenticatorElement
                    .getAttributeValue("module-name");
        Element authorizerElement = getPersist()
                .getChild("qp-authorizer");
        if (authorizerElement != null)
            authorizerModuleName = authorizerElement
                    .getAttributeValue("module-name");

        for (Element repoElement :
                getPersist().getChildren("qp-repository")) {
            try {
                QPRepositoryConstruct repositoryConstruct = new QPRepositoryConstruct();
                repositoryConstruct.setName(repoElement.getAttributeValue("name"));
                repositoryConstruct.setSpringBeanProvider(
                        getSpringContext(repoElement.getAttributeValue(
                                "spring-context", "default")));
                repositoryConstruct.setScope(repoElement
                        .getAttributeValue("scope", "singleton"));
                Class c = Class.forName(repoElement.getAttributeValue("class"));
                repositoryConstruct.setaClass(c);
                Object o = c.newInstance();
                if (o instanceof QPHttpRepository) {
                    ((QPHttpRepository) o).springBeanProvider =
                            repositoryConstruct.getSpringBeanProvider();
                    repositoryConstruct.setSingletonInstance(o);
                } else
                    throw new Exception("repository " +
                            repositoryConstruct.getName() +
                            "not implemented QPHttpRepository interface!");
                repositoryManagerMap.put(repositoryConstruct.getName(),
                        QPHttpRepositoryManagerBuilder.build(repositoryConstruct));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (QPException e) {
                e.printStackTrace();
            }
        }

        getPersist().getChildren("qp-handler")
                .parallelStream().forEach(handlerElement -> {
            QPHandlerConstruct handlerConstruct = new QPHandlerConstruct();
            String url = handlerElement.getAttributeValue("url");
            handlerConstruct.setMethod(handlerElement.getAttributeValue("method"));
            handlerConstruct.setRoles(handlerElement.getAttributeValue("roles"));
            if (handlerConstruct.getRoles() == null || handlerConstruct.getRoles().isEmpty()) {
                handlerConstruct.setRoleProvidables(new ArrayList<>());
            } else {
                List<QPRoleProvidable> roleProvidables = Arrays.stream(
                        handlerConstruct.getRoles().split(","))
                        .map(role -> new QPRoleProvider(role))
                        .collect(Collectors.toList());
                handlerConstruct.setRoleProvidables(roleProvidables);
            }

            String repoName = handlerElement.getAttributeValue("repository");
            handlerConstruct.setRepoManager(repositoryManagerMap.get(repoName));
            String handlerName = handlerElement.getAttributeValue("handler");
            handlerConstruct.setHandlerName(handlerName);

            handlerConstruct.setHandlerConfig(QPHttpInjectableConfigImpl
                    .build(handlerElement.getChildren("property")));

            if (url.contains("**")) {
                handlerConstruct.setUrl(url.substring(
                        0, url.indexOf("/**")));
                httpAsteriskHandlerConstructMap.put(
                        handlerConstruct.getMethod()
                                .concat(":")
                                .concat(url.substring(0, url.indexOf("/**"))),
                        handlerConstruct);
            } else {
                handlerConstruct.setUrl(url);
                handlerConstructMap.put(
                        handlerConstruct.getMethod()
                                .concat(":")
                                .concat(handlerConstruct.getUrl()),
                        handlerConstruct);
            }

            QPRoleManagerModule roleManagerModule = null;
            if (authorizerModuleName != null)
                roleManagerModule = QPBaseModule
                        .getModule(authorizerModuleName, QPRoleManagerModule.class);
            if (roleManagerModule != null) {
                try {
                    handlerConstruct.setRolesId(roleManagerModule.createRolesId(
                            handlerConstruct.getRoleProvidables()));
                } catch (QPException e) {
                    e.printStackTrace();
                }
            } else {
                handlerConstruct.setRolesId(0l);
            }
        });
    }

    @Override
    protected void initAfterRegisterQPModule() throws Exception {
        listener = Executors.newSingleThreadExecutor();
        worker = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void startQPModule() throws Exception {
        listener.submit(() -> {
            while (getState() == QBean.STARTED) {
                try {
                    Context context = in(Context.class);
                    worker.execute(() -> {
                        processRequest(context);
                    });
                } catch (QPException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void stopQPModule() throws Exception {

    }

    @Override
    protected void destroyQPModule() throws Exception {

    }

    @Override
    public void notify(String key, Context value) {

    }

    protected void processRequest(Context context) {
        QPHttpRequest request = null;
        QPHttpResponse response = null;
        try {
            request = context.get("qp-httpmodule-request");
            response = context.get("qp-httpmodule-response");
            String requestKey = request.getMethodType().getName()
                    .concat(":")
                    .concat(request.getRequestURI());
            QPHandlerConstruct handlerConstruct = handlerConstructMap
                    .get(requestKey);
            String asteriskPath = "";
            if (handlerConstruct == null) {
                String selectedKey = "";
                for (String key : httpAsteriskHandlerConstructMap.keySet()) {
                    if (requestKey.startsWith(key)) {
                        if (selectedKey.length() < key.length()) {
                            selectedKey = key;
                        }
                    }
                }
                if (!selectedKey.isEmpty()) {
                    handlerConstruct = httpAsteriskHandlerConstructMap
                            .get(selectedKey);
                    asteriskPath = requestKey.substring(selectedKey.length());
                    request.setAsteriskPath(asteriskPath);
                }
            }
            if(handlerConstruct != null) {
                QPHttpAuthenticated qpHttpAuthenticated = authenticateHttpHandler(
                        handlerConstruct,
                        request, response);
                if(handlerConstruct.getRolesId() > 0)
                    if(!authorizeHttpHandler(handlerConstruct, qpHttpAuthenticated))
                        throw new QPHttpException(QPHttpStatus.UNAUTHORIZED_401);

                invokeHttpHandler(qpHttpAuthenticated,
                        handlerConstruct,
                        request, response);
            } else {
                throw new QPHttpException(QPHttpStatus.NOT_FOUND_404);
            }
        } catch (QPHttpException e) {
            e.applyResponse(response);
        } catch (QPException e) {
            new QPHttpException(
                    QPHttpStatus.INTERNAL_SERVER_ERROR_500)
                    .applyResponse(response);
        } catch (Exception e) {
            new QPHttpException(
                    QPHttpStatus.INTERNAL_SERVER_ERROR_500)
                    .applyResponse(response);
        }
    }

    protected QPHttpAuthenticated authenticateHttpHandler(
            QPHandlerConstruct handlerConstruct,
            QPHttpRequest request, QPHttpResponse response)
            throws QPException {
        if(authenticatorModuleName != null) {
            QPHttpAuthenticator module = QPBaseModule
                    .getModule(authenticatorModuleName, QPHttpAuthenticator.class);
            QPHttpAuthenticated authenticated = module.authenticate(request);
            return authenticated;
        }
        return null;
    }

    protected boolean authorizeHttpHandler(
            QPHandlerConstruct handlerConstruct,
            QPHttpAuthenticated authenticated)
            throws QPException {
        if(authenticated != null) {
            if (authorizerModuleName != null) {
                QPRoleManagerModule roleManagerModule = QPBaseModule
                        .getModule(authorizerModuleName,
                                QPRoleManagerModule.class);
                if (!roleManagerModule.isRegistered(
                        authenticated.getPrincipal()))
                    roleManagerModule.registerPrincipalRoles(
                            authenticated.getPrincipal(),
                            authenticated.getAuthenticatedRoles());
                long l = roleManagerModule.hasAnyRole(
                        authenticated.getPrincipal(),
                        handlerConstruct.getRolesId());
                if(l > 0)
                    return true;
            }
        }
        return false;
    }

    protected void invokeHttpHandler(QPHttpAuthenticated authenticated,
                                     QPHandlerConstruct handlerConstruct,
                                     QPHttpRequest request, QPHttpResponse response)
            throws QPException {
        PrincipalEntity principal = null;
        if(authenticated!= null)
            principal = authenticated.getPrincipal();
        handlerConstruct.getRepoManager()
                .resolve(handlerConstruct.getHandlerName())
                .handle(principal, handlerConstruct.getHandlerConfig(),
                        request, response);
        response.apply();
    }

//    protected void invokeHttpOperator(
//            QPServiceConstruct serviceConstruct,
//            QPHttpRequest request, QPHttpResponse response)
//            throws Exception {
//        String[] split = serviceConstruct.getHandler().split(":");
//        String handlerClass = pkgMap.get(
//                serviceConstruct.getPkgName())
//                .concat(".")
//                .concat(split[0]);
//        Class<?> aClass = Class.forName(handlerClass);
//        Method method = aClass.getMethod(
//                split[1], QPHttpRequest.class, QPHttpResponse.class);
//        method.invoke(null, request, response);
//        response.apply();
//    }
}
