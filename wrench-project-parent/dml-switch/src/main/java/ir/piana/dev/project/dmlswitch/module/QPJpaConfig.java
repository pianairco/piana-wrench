package ir.piana.dev.project.dmlswitch.module;

import ir.piana.dev.wrench.core.module.QPSpringDataConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

/**
 * @author Mohammad Rahmati, 2/21/2019
 */
@Configuration
@EnableJpaRepositories(
        transactionManagerRef = "QpTransactionManager",
        entityManagerFactoryRef = "QpEntityManagerFactory")
@PropertySource(value = "file:./application.properties")
@EnableTransactionManagement(mode = AdviceMode.PROXY)
public class QPJpaConfig extends QPSpringDataConfig {
    @Value("${qp.spring.jpa.module.qp}")
    private String qpJpaModuleName;

    @Override
    protected String getJpaModuleName() {
        return qpJpaModuleName;
    }

    @Bean(name = "QpEntityManagerFactory")
    public EntityManagerFactory getEntityManagerFactory() {
        return super.entityManagerFactory();
    }

    @Bean(name = "QpTransactionManager")
    protected JpaTransactionManager getTransactionManager(
            @Qualifier("QpEntityManagerFactory")
                    EntityManagerFactory entityManagerFactory) {
        return super.transactionManager(entityManagerFactory);
    }
}
