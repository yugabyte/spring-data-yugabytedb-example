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
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest(properties = {
        "spring.sql.init.schema-locations=classpath:sql/create_table.sql",
        "spring.sql.init.mode=always",
        "spring.datasource.url=jdbc:tc:yugabyte:2.14.4.0-b26:///"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(YsqlConfig.class)
@Testcontainers
@ActiveProfiles("test")
public class CustomerYsqlRepositoryHostlessTest {

    @Autowired
    private CustomerYsqlRepository repository;

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
