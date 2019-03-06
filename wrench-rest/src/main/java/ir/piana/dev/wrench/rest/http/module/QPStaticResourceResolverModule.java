package ir.piana.dev.wrench.rest.http.module;

import ir.piana.dev.wrench.core.module.QPBaseModule;
import ir.piana.dev.wrench.rest.http.staticresource.QPStaticResource;
import ir.piana.dev.wrench.rest.http.staticresource.QPStaticResourceResolver;

/**
 * @author Mohammad Rahmati, 2/17/2019
 */
public class QPStaticResourceResolverModule extends QPBaseModule {
    private String rootPath;
    protected QPStaticResourceResolver staticResourceResolver;

    @Override
    protected void configBeforeRegisterQPModule() throws Exception {
        rootPath = cfg.get("root-path");
    }

    @Override
    protected void initBeforeRegisterQPModule() throws Exception {
        staticResourceResolver = QPStaticResourceResolver
                .getInstance(rootPath);
    }

    @Override
    protected void configForSpringContext() throws Exception {

    }

    @Override
    protected void configAfterRegisterQPModule() throws Exception {

    }

    @Override
    protected void initAfterRegisterQPModule() throws Exception {

    }

    @Override
    protected void startQPModule() throws Exception {

    }

    @Override
    protected void stopQPModule() throws Exception {

    }

    @Override
    protected void destroyQPModule() throws Exception {

    }

    public QPStaticResource resolve(String path) throws Exception {
        return staticResourceResolver.resolve(path);
    }
}
