package ir.piana.dev.project.dmlswitch.module;

import ir.piana.dev.wrench.core.module.QPBaseModule;

/**
 * @author Mohammad Rahmati, 3/5/2019
 */
public class DmlSwitchModule extends QPBaseModule {
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
//        ISOMsg isoMsg = new ISOMsg("0200");
//        isoMsg.set(2, "6104337804210969");
//        isoMsg.set(3, "100");
//        out(isoMsg);
    }

    @Override
    protected void stopQPModule() throws Exception {

    }

    @Override
    protected void destroyQPModule() throws Exception {

    }
}
