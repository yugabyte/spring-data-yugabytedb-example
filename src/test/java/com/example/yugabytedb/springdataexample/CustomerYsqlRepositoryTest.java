package com.example.yugabytedb.springdataexample;

import com.example.yugabytedb.springdataexample.config.YsqlConfig;
import com.example.yugabytedb.springdataexample.domain.Customer;
import com.example.yugabytedb.springdataexample.repo.CustomerYsqlRepository;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.YugabyteDBYSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest(properties = {
        "spring.sql.init.schema-locations=classpath:sql/create_table.sql",
        "spring.sql.init.mode=always"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(YsqlConfig.class)
@Testcontainers
@ActiveProfiles("test")
public class CustomerYsqlRepositoryTest {

    @Container
    private static final YugabyteDBYSQLContainer yugabyte = new YugabyteDBYSQLContainer("yugabytedb/yugabyte:2.14.4.0-b26");

    @Autowired
    private CustomerYsqlRepository repository;

    @DynamicPropertySource
    static void registry(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", yugabyte::getJdbcUrl);
        registry.add("spring.datasource.username", yugabyte::getUsername);
        registry.add("spring.datasource.password", yugabyte::getPassword);
        registry.add("spring.datasource.driver-class-name", yugabyte::getDriverClassName);
    }

    @Test
    void test() {
        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        Customer customer = new Customer(person.passportNumber(),
                person.fullName(),
                person.email(),
                person.getAddress().toString(),
                person.dateOfBirth().toString());
        this.repository.save(customer);

        assertThat(this.repository.findByEmail(person.email()))
                .extracting("id", "name", "email", "address", "birthday")
                .containsExactly(person.passportNumber(), person.fullName(), person.email(), person.getAddress().toString(), person.dateOfBirth().toString());
    }
}
