package ir.piana.dev.wrench.core.db;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;

/**
 * @author Mohammad Rahmati, 1/22/2019
 */
public final class QPEntityManagerFactoryBuilder {
    private EntityManagerFactoryInfo entityManagerFactoryInfo;

    private QPEntityManagerFactoryBuilder(
            EntityManagerFactoryInfo entityManagerFactoryInfo) {
        this.entityManagerFactoryInfo = entityManagerFactoryInfo;
    }

    public static EntityManagerFactory build(
            EntityManagerFactoryInfo jpaInfo) {
        return new QPEntityManagerFactoryBuilder(jpaInfo)
                .entityManagerFactory();
    }

    protected String getDatabasePlatformClassName(
            String databasePlatformName) {
        if(databasePlatformName.equalsIgnoreCase("mysql"))
            return "org.eclipse.persistence.platform.database.MySQLPlatform";
        else if(databasePlatformName.equalsIgnoreCase("oracle"))
            return "org.eclipse.persistence.platform.database.Oracle11Platform";
        else if(databasePlatformName.equalsIgnoreCase("derby"))
            return "org.eclipse.persistence.platform.database.DerbyPlatform";
        return null;
    }

    protected Database getDatabase(String databasePlatformName) {
        if(databasePlatformName.equalsIgnoreCase("mysql"))
            return Database.MYSQL;
        else if(databasePlatformName.equalsIgnoreCase("oracle"))
            return Database.ORACLE;
        else if(databasePlatformName.equalsIgnoreCase("derby"))
            return Database.DERBY;
        return null;
    }

    protected EclipseLinkJpaVendorAdapter eclipseLinkJpaVendorAdapter(
            String databasePlatformClassName, Database database) {
        EclipseLinkJpaVendorAdapter vendorAdapter =
                new EclipseLinkJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(databasePlatformClassName);
        vendorAdapter.setDatabase(database);
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    protected JpaVendorAdapter hibernateJpaVendorAdapter(
            String databasePlatformClassName, Database database) {
        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(databasePlatformClassName);
        vendorAdapter.setDatabase(database);
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    protected JpaVendorAdapter jpaVendorAdapter(
            String jpaVendorAdaptorName,
            String databasePlatformName) {
        String databasePlatformClassName =
                getDatabasePlatformClassName(databasePlatformName);
        Database database = getDatabase(databasePlatformName);
        if(jpaVendorAdaptorName.equalsIgnoreCase("eclipselink")) {
            return eclipseLinkJpaVendorAdapter(
                    databasePlatformClassName, database);
        } else if(jpaVendorAdaptorName.equalsIgnoreCase("hibernate"))
            return hibernateJpaVendorAdapter(
                    databasePlatformClassName, database);
        return null;
    }

    protected EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(entityManagerFactoryInfo.getDatasource());
        factory.setJpaVendorAdapter(jpaVendorAdapter(
                entityManagerFactoryInfo.getJpaProvider(),
                entityManagerFactoryInfo.getDatabasePlatform()));
        factory.getJpaPropertyMap()
                .put("eclipselink.weaving", "false");
//        factory.setPersistenceUnitName(entityManagerFactoryInfo.getPersistenceUnitName());
        factory.setPackagesToScan(entityManagerFactoryInfo.getBasePackages());

//        PersistenceUnitInfo persistenceUnitInfo =
//                persistenceUnitInfo(getClass().getSimpleName())

        // This will trigger the creation of the product manager factory
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    protected JpaTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}
