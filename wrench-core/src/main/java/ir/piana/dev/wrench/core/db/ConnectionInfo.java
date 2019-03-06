package ir.piana.dev.wrench.core.db;

/**
 * @author Mohammad Rahmati, 12/12/2018
 */
public class ConnectionInfo {
    private String instanceName;
    private String databasePlatform;
    private String jdbcUrl;
    private String driverClassName;
    private String user;
    private String password;
    private String sid;
    private int poolSize;

    public ConnectionInfo(
            String instanceName,
            String databasePlatform,
            String jdbcUrl,
            String driverClassName,
            String user,
            String password,
            String sid,
            int poolSize) {
        this.instanceName = instanceName;
        this.databasePlatform = databasePlatform;
        this.jdbcUrl = jdbcUrl;
        this.driverClassName = driverClassName;
        this.user = user;
        this.password = password;
        this.sid = sid;
        this.poolSize = poolSize;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getDatabasePlatform() {
        return databasePlatform;
    }

    public void setDatabasePlatform(String databasePlatform) {
        this.databasePlatform = databasePlatform;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }
}
