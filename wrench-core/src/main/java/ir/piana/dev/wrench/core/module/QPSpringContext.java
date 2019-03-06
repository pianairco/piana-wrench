package ir.piana.dev.wrench.core.module;

/**
 * @author Mohammad Rahmati, 2/19/2019
 */
public interface QPSpringContext extends QPSpringBeanProvider {
//    void refresh();
    <T> QPSpringContext register(Class<T> beanClass);
    <T> QPSpringContext registerBean(String beanName, Class<T> beanClass);
}
