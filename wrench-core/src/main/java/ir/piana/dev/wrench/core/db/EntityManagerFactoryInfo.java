package ir.piana.dev.wrench.core.db;

import javax.sql.DataSource;

/**
 * @author Mohammad Rahmati, 1/30/2019
 */
public class EntityManagerFactoryInfo {
    private String instanceName;
    private DataSource datasource;
    private String databasePlatform;
    private String jpaProvider;
    private String persistenceUnitName;
    private String[] basePackages;

    public EntityManagerFactoryInfo(
            String instanceName,
            DataSource datasource,
            String databasePlatform,
            String jpaProvider,
            String persistenceUnitName,
            String[] basePackages) {
        this.instanceName = instanceName;
        this.datasource = datasource;
        this.databasePlatform = databasePlatform;
        this.jpaProvider = jpaProvider;
        this.persistenceUnitName = persistenceUnitName;
        this.basePackages = basePackages;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public DataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }

    public String getDatabasePlatform() {
        return databasePlatform;
    }

    public void setDatabasePlatform(String databasePlatform) {
        this.databasePlatform = databasePlatform;
    }

    public String getJpaProvider() {
        return jpaProvider;
    }

    public void setJpaProvider(String jpaProvider) {
        this.jpaProvider = jpaProvider;
    }

    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    public void setPersistenceUnitName(String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
    }

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }
}
