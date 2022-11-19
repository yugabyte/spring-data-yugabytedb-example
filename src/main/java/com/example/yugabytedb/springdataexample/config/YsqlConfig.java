package com.example.yugabytedb.springdataexample.config;

import com.example.yugabytedb.springdataexample.repo.CustomerYsqlRepository;
import com.yugabyte.data.jdbc.datasource.YugabyteTransactionManager;
import com.yugabyte.data.jdbc.repository.config.AbstractYugabyteJdbcConfiguration;
import com.yugabyte.data.jdbc.repository.config.EnableYsqlRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@EnableYsqlRepositories(basePackageClasses = CustomerYsqlRepository.class)
public class YsqlConfig extends AbstractYugabyteJdbcConfiguration {

    @Bean
    TransactionManager transactionManager(DataSource dataSource) {                     
        return new YugabyteTransactionManager(dataSource);
    }

}