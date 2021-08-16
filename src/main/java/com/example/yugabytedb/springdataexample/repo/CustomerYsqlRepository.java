package com.example.yugabytedb.springdataexample.repo;


import org.springframework.stereotype.Repository;
import com.example.yugabytedb.springdataexample.domain.Customer;
import com.yugabyte.data.jdbc.repository.YsqlRepository;

@Repository
public interface CustomerYsqlRepository extends YsqlRepository<Customer, Integer> {

	Customer findByEmail(final String email);

}
