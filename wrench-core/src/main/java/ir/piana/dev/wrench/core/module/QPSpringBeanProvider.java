package ir.piana.dev.wrench.core.module;

/**
 * @author Mohammad Rahmati, 2/19/2019
 */
public interface QPSpringBeanProvider {
    <T> T getBean(String beanName, Class<T> beanClass);
    <T> T getBean(Class<T> beanClass);
}
