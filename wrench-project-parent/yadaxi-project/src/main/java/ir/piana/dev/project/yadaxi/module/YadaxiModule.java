package ir.piana.dev.project.yadaxi.module;

import ir.piana.dev.wrench.core.module.QPBaseModule;

/**
 * @author Mohammad Rahmati, 2/21/2019
 */
public class YadaxiModule extends QPBaseModule {
    @Override
    protected void configBeforeRegisterQPModule() throws Exception {

    }

    @Override
    protected void initBeforeRegisterQPModule() throws Exception {

    }

    @Override
    protected void configForSpringContext() throws Exception {
        getSpringContext()
                .register(QPJpaConfig.class);
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
}
