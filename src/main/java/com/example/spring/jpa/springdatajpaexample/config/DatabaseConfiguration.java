package com.example.spring.jpa.springdatajpaexample.config;

import com.yugabyte.spring.AbstractYugabyteConfiguration;
import com.yugabyte.ysql.YBClusterAwareDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration extends AbstractYugabyteConfiguration {

    /*
     * Required: Set package where the Entity classes are declared.
     */
    @Override
    public String packagesToScan() {
        return "com.example.spring.jpa.springdatajpaexample.domain";
    }

    /*
     * Optional: Override generateDdl to set whether to generate DB objects for the managed entities as needed.
     * Default: false.
     */
    @Override
    public boolean generateDdl() {
        return true;
    }

    /*
     * Optional: Override the dataSource bean to configure the data source.
     * Default: create a YBClusterAwareDataSource() with default settings.
     */
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
        ds.setAutoCommit(true);
        ds.setConnectionTimeoutMs(10000); // 10 seconds.
        return ds;
    }
}