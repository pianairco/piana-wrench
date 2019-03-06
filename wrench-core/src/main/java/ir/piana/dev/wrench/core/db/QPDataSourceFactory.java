package ir.piana.dev.wrench.core.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ir.piana.dev.wrench.core.QPException;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * @author Mohammad Rahmati, 12/12/2018
 */
public class QPDataSourceFactory {
    ConnectionInfo connectionInfo;
    HikariConfig hikariConfig;
    HikariDataSource hikariDataSource;

    private QPDataSourceFactory(
            ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public static DataSource createDatasource(
            ConnectionInfo connectionInfo) throws QPException {
        try {
            QPDataSourceFactory factory =
                    new QPDataSourceFactory(connectionInfo);
            factory.initialize();
            return factory.hikariDataSource;
        } catch (Exception e) {
            throw new QPException(e);
        }
    }

    public void initialize()
            throws Exception {
        Properties props = new Properties();
        props.setProperty("jdbcUrl", connectionInfo.getJdbcUrl());
        props.setProperty("driverClassName", connectionInfo.getDriverClassName());
        props.setProperty("dataSource.user", connectionInfo.getUser());
        props.setProperty("dataSource.password", connectionInfo.getPassword());
        props.setProperty("dataSource.databaseName", connectionInfo.getSid());
        props.put("dataSource.logWriter", new PrintWriter(System.out));

        hikariConfig = new HikariConfig(props);
        hikariDataSource = new HikariDataSource(hikariConfig);
        hikariDataSource.setMaximumPoolSize(connectionInfo.getPoolSize());
    }
}
