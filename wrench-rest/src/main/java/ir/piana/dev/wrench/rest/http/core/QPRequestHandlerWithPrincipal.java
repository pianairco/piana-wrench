package ir.piana.dev.wrench.rest.http.core;

import ir.piana.dev.wrench.core.QPException;
import ir.piana.dev.wrench.core.module.QPBaseModule;
import ir.piana.dev.wrench.rest.authenticate.entity.PrincipalEntity;
import ir.piana.dev.wrench.rest.authorize.QPRoleManagerModule;
import ir.piana.dev.wrench.rest.http.construct.QPHandlerConstruct;
import ir.piana.dev.wrench.rest.http.core.*;

/**
 * @author Mohammad Rahmati, 3/10/2019
 */
public class QPRequestHandlerWithPrincipal
        implements QPRequestHandler {
    private String authenticatorModuleName;
    private String authorizerModuleName;
    private QPHandlerConstruct handlerConstruct;

    public QPRequestHandlerWithPrincipal(
            String authenticatorModuleName,
            String authorizerModuleName,
            QPHandlerConstruct handlerConstruct) {
        this.authenticatorModuleName = authenticatorModuleName;
        this.authorizerModuleName = authorizerModuleName;
        this.handlerConstruct = handlerConstruct;
    }

    @Override
    public QPHttpResponse handle(QPHttpRequest request)
            throws QPHttpException {
        QPHttpAuthenticated authenticated = authenticateHttpHandler(request);
        authorizeHttpHandler(authenticated);
        return invokeHttpHandlerWithPrincipal(
                authenticated,
                request);
    }

    protected QPHttpAuthenticated authenticateHttpHandler(
            QPHttpRequest request)
            throws QPHttpException {
        if(authenticatorModuleName != null) {
            QPHttpAuthenticator module = QPBaseModule
                    .getModule(authenticatorModuleName, QPHttpAuthenticator.class);
            QPHttpAuthenticated authenticated = module.authenticate(request);
            return authenticated;
        }
        return null;
    }

    protected void authorizeHttpHandler(
            QPHttpAuthenticated authenticated)
            throws QPHttpException {
        if(handlerConstruct.getRolesId() == 0)
            return;
        if(authenticated != null) {
            if (authorizerModuleName != null) {
                QPRoleManagerModule roleManagerModule = QPBaseModule
                        .getModule(authorizerModuleName,
                                QPRoleManagerModule.class);
                if (!roleManagerModule.isRegistered(
                        authenticated.getPrincipal())) {
                    try {
                        roleManagerModule.registerPrincipalRoles(
                                authenticated.getPrincipal(),
                                authenticated.getAuthenticatedRoles());
                    } catch (QPException e) {
                        throw new QPHttpException(QPHttpStatus.UNAUTHORIZED_401);
                    }
                }
                long l = roleManagerModule.hasAnyRole(
                        authenticated.getPrincipal(),
                        handlerConstruct.getRolesId());
                if(l == 0)
                    throw new QPHttpException(QPHttpStatus.UNAUTHORIZED_401);
            }
        }
        throw new QPHttpException(QPHttpStatus.UNAUTHORIZED_401);
    }

    protected QPHttpResponse invokeHttpHandlerWithPrincipal(
            QPHttpAuthenticated authenticated,
            QPHttpRequest request)
            throws QPHttpException {
        PrincipalEntity principal = null;
        if(authenticated!= null)
            principal = authenticated.getPrincipal();
        QPHttpResponse response = null;
        try {
            response = handlerConstruct.getRepoManager()
                    .resolveWithPrincipal(handlerConstruct.getHandlerName())
                    .handle(request, handlerConstruct.getHandlerConfig(),
                            principal);
        } catch (QPException e) {
            throw new QPHttpException(QPHttpStatus.BAD_REQUEST_400);
        }
        return response;
    }
}
