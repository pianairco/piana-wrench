package ir.piana.dev.wrench.core.module;

import ir.piana.dev.wrench.core.db.ConnectionInfo;
import ir.piana.dev.wrench.core.db.EntityManagerFactoryInfo;
import ir.piana.dev.wrench.core.db.QPDataSourceFactory;
import ir.piana.dev.wrench.core.db.QPEntityManagerFactoryBuilder;
import org.jdom2.Element;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class QPEntityManagerFactoryProviderModule
        extends QPBaseModule
        implements QPEntityManagerFactoryProvider {
    protected ConnectionInfo connectionInfo;
    protected DataSource datasource;
    protected EntityManagerFactoryInfo entityManagerFactoryInfo;
    protected EntityManagerFactory entityManagerFactory;

    @Override
    protected void configBeforeRegisterQPModule() throws Exception {
        Element connectionElement = getPersist().getChild("datasource");
        String datasourceName = connectionElement
                .getAttributeValue("name");
        String databasePlatform = connectionElement
                .getChildText("database-platform");
        String jdbcUrl = connectionElement
                .getChildText("jdbc-url");
        String driverClassName = connectionElement
                .getChildText("driver-class-name");
        String user = connectionElement
                .getChildText("user");
        String password = connectionElement
                .getChildText("password");
        String sid = connectionElement
                .getChildText("sid");
        String poolSizeString = connectionElement
                .getChildText("pool-size");
        Integer poolSize = poolSizeString == null ?
                100 : new Integer(poolSizeString);
        connectionInfo = new ConnectionInfo(
                datasourceName, databasePlatform,
                jdbcUrl, driverClassName,
                user, password,
                sid, poolSize);


        datasource = QPDataSourceFactory
                .createDatasource(connectionInfo);

        Element emfElement = getPersist().getChild("entity-manager-factory");
        String emfName = emfElement
                .getAttributeValue("name");
        String emfDatabasePlatform = emfElement
                .getChildText("database-platform");
        String jpaProvider = emfElement
                .getChildText("jpa-provider");
        String persistenceUnitName = emfElement
                .getChildText("persistence-unit-name");
        List<Element> children = emfElement
                .getChildren("base-package");
        final List<String> basePackageList = new ArrayList<>();
        children.forEach(el -> {
            basePackageList.add(el.getValue());
        });
        String[] basePackages = new String[basePackageList.size()];
        basePackages = basePackageList.toArray(basePackages);

        entityManagerFactoryInfo = new EntityManagerFactoryInfo(
                emfName,
                datasource,
                databasePlatform,
                jpaProvider,
                persistenceUnitName,
                basePackages);
    }

    @Override
    protected void initBeforeRegisterQPModule() throws Exception {

    }

    @Override
    protected void configForSpringContext() throws Exception {
        entityManagerFactory = QPEntityManagerFactoryBuilder
                .build(entityManagerFactoryInfo);
    }

    @Override
    protected void initAfterRegisterQPModule() throws Exception {

    }

    @Override
    protected void configAfterRegisterQPModule() throws Exception {

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

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
