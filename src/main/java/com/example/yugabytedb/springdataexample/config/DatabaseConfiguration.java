package com.example.yugabytedb.springdataexample.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import com.example.yugabytedb.springdataexample.repo.CustomerJpaRepository;

@Configuration
@EnableJdbcRepositories(basePackageClasses = CustomerJpaRepository.class)
public class DatabaseConfiguration extends AbstractJdbcConfiguration {

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

     @Bean
 DataSource dataSource() {
     DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
     driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5433/yugabyte");
     driverManagerDataSource.setUsername("yugabyte");
     driverManagerDataSource.setPassword("");
     driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
     return driverManagerDataSource;
 }

 @Bean
 JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {

     JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
     return jdbcTemplate;
 }
 
   @Bean
   NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource dataSource) { 
       return new NamedParameterJdbcTemplate(dataSource);
   }

   @Bean
   TransactionManager transactionManager(DataSource dataSource) {                     
       return new DataSourceTransactionManager(dataSource);
   }
}