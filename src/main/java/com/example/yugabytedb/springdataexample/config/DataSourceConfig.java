package com.example.yugabytedb.springdataexample.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Profile("!test")
public class DataSourceConfig {

    @Bean
    DataSource dataSource() {
        String hostName = "127.0.0.1";
        String port = "5433";

        Properties poolProperties = new Properties();
        poolProperties.setProperty("dataSourceClassName", "com.yugabyte.ysql.YBClusterAwareDataSource");
        poolProperties.setProperty("dataSource.serverName", hostName);
        poolProperties.setProperty("dataSource.portNumber", port);
        poolProperties.setProperty("dataSource.user", "yugabyte");
        poolProperties.setProperty("dataSource.password", "yugabyte");
        poolProperties.setProperty("dataSource.additionalEndpoints",
                "127.0.0.2:5433,127.0.0.3:5433");

        HikariConfig hikariConfig = new HikariConfig(poolProperties);
        DataSource ybClusterAwareDataSource = new HikariDataSource(hikariConfig);
        return ybClusterAwareDataSource;
    }

}
