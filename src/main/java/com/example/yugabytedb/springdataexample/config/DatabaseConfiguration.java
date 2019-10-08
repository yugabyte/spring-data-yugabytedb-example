package com.example.yugabytedb.springdataexample.config;

import com.yugabyte.spring.AbstractYugabyteConfiguration;
import com.yugabyte.ysql.YBClusterAwareDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration extends AbstractYugabyteConfiguration {

    // Optional: Override the dataSource bean to customize the data source.
    // Default: create a YBClusterAwareDataSource() with default settings.
    // The sample code below is the equivalent of the (data source) config from the application.properties.
    /*
    @Override
    @Bean
    public DataSource dataSource() {
        YBClusterAwareDataSource ds = new YBClusterAwareDataSource();

        // Configure the datasource.
        // Note: Configuration options below are set for illustration only as
        //       the values set below are all the YBClusterAwareDataSource's defaults.
        ds.setInitialHost("localhost");
        ds.setPort(5433);
        ds.setDatabase("yugabyte");
        ds.setUser("yugabyte");
        ds.setPassword("yugabyte");
        ds.setMaxPoolSizePerNode(8);
        ds.setConnectionTimeoutMs(10000); // 10 seconds.
        return ds;
    }
    */
}