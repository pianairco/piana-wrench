package ir.piana.dev.wrench.core.module;

import ir.piana.dev.wrench.core.QPException;
import org.jpos.q2.QBeanSupport;
import org.jpos.space.LocalSpace;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.space.SpaceListener;
import org.jpos.util.NameRegistrar;

public abstract class QPBaseModule extends QBeanSupport {
    private boolean willBeRegistered;
    private Space space;
    private String spaceName;
    private String inQueue;
    private String outQueue;
    private String springContextName;

    private QPSpringContextFactory pianaSpringContextFactory =
            QPSpringContextFactory.getContextFactory();

    @Override
    protected final void initService()
            throws Exception {
        willBeRegistered = cfg.getBoolean("qp-will-be-registered", true);
        spaceName = cfg.get("qp-space", "tspace:default");
        String defaultQueue = "";
        for (String name : this.getClass().getSimpleName()
                .split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
            defaultQueue = defaultQueue
                    .concat(name.toLowerCase())
                    .concat("-");
        }
        inQueue = cfg.get("qp-in", defaultQueue.concat("in"));
        outQueue = cfg.get("qp-out", defaultQueue.concat("out"));
        springContextName = cfg.get("spring-context", "default");
        space = SpaceFactory.getSpace(spaceName);
        configBeforeRegisterQPModule();
        initBeforeRegisterQPModule();
        if(willBeRegistered)
            NameRegistrar.register(getName(), this);
        configForSpringContext();
    }

    @Override
    protected final void startService()
            throws Exception {
        configAfterRegisterQPModule();
        initAfterRegisterQPModule();
        startQPModule();
    }

    @Override
    protected final void stopService()
            throws Exception {
        if(willBeRegistered)
            NameRegistrar.unregister(getName());
        stopQPModule();
    }

    @Override
    protected final void destroyService()
            throws Exception {
        destroyQPModule();
    }

    protected QPSpringContext getSpringContext() {
        return pianaSpringContextFactory
                .getSpringContext(springContextName);
    }

    protected QPSpringContext getSpringContext(String springContextName) {
        return pianaSpringContextFactory
                .getSpringContext(springContextName);
    }

    protected abstract void configBeforeRegisterQPModule() throws Exception;

    protected abstract void initBeforeRegisterQPModule() throws Exception;

    protected abstract void configForSpringContext() throws Exception;

    protected abstract void configAfterRegisterQPModule() throws Exception;

    protected abstract void initAfterRegisterQPModule() throws Exception;

    protected abstract void startQPModule() throws Exception;

    protected abstract void stopQPModule() throws Exception;

    protected abstract void destroyQPModule() throws Exception;

    protected final void out(Object object) {
        space.out(outQueue, object);
    }

    protected final void setSpaceListener(SpaceListener spaceListener) {
        ((LocalSpace)space).addListener(inQueue, spaceListener);
    }

    protected Object in() {
        return space.in(inQueue);
    }

    protected <T> T in(Class objType)
            throws QPException {
        Object in = space.in(inQueue);
        if(objType.isInstance(in))
            return (T) in;
        throw new QPException("type conversion error");
    }

    public static final <T> T getModule(
            String moduleInstanceName) {
        return NameRegistrar.getIfExists(moduleInstanceName);
    }

    public static final <T> T getModule(
            String moduleInstanceName, Class<T> moduleType) {
        return (T)NameRegistrar.getIfExists(moduleInstanceName);
    }
}
