package com.example.yugabytedb.springdataexample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.YugabyteDBYSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.sql.init.schema-locations=classpath:sql/create_table.sql",
                "spring.sql.init.mode=always"})
@Testcontainers
@ActiveProfiles("test")
public class SpringDataJpaExampleApplicationTest {

    @LocalServerPort
    private int serverPort;

    @Container
    private static final YugabyteDBYSQLContainer yugabyte = new YugabyteDBYSQLContainer("yugabytedb/yugabyte:2.14.4.0-b26");

    @DynamicPropertySource
    static void registry(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", yugabyte::getJdbcUrl);
        registry.add("spring.datasource.username", yugabyte::getUsername);
        registry.add("spring.datasource.password", yugabyte::getPassword);
        registry.add("spring.datasource.driver-class-name", yugabyte::getDriverClassName);
    }

    @Test
    void test() {
        given().port(this.serverPort)
                .get("/loaddb")
                .then()
                .statusCode(200)
                .body(containsString("Current customer count: 10"));

        given().port(this.serverPort)
                .get("/showdb")
                .then()
                .statusCode(200)
                .body(containsString("Showing results 1-10 out of 10"));
    }

}
