package ir.piana.dev.wrench.rest.http.construct;

import ir.piana.dev.wrench.core.module.QPSpringBeanProvider;

/**
 * @author Mohammad Rahmati, 2/16/2019
 */
public class QPRepositoryConstruct {
    private String name;
    private QPSpringBeanProvider springBeanProvider;
    private Class aClass;
    private String scope;
    private Object singletonInstance;

    public QPRepositoryConstruct() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Object getSingletonInstance() {
        return singletonInstance;
    }

    public void setSingletonInstance(Object singletonInstance) {
        this.singletonInstance = singletonInstance;
    }

    public QPSpringBeanProvider getSpringBeanProvider() {
        return springBeanProvider;
    }

    public void setSpringBeanProvider(QPSpringBeanProvider springBeanProvider) {
        this.springBeanProvider = springBeanProvider;
    }
}
