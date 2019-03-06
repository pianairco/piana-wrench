package ir.piana.dev.wrench.core.module;

import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;

/**
 * @author Mohammad Rahmati, 1/22/2019
 */
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public abstract class QPSpringDataConfig {
    protected abstract String getJpaModuleName();

    protected final EntityManagerFactory entityManagerFactory() {
        QPEntityManagerFactoryProvider module = QPBaseModule
                .getModule(getJpaModuleName());
        return module.getEntityManagerFactory();
    }

    protected final JpaTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}
