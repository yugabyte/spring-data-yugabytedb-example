package com.example.yugabytedb.springdataexample.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.yugabytedb.springdataexample.domain.Customer;

@Repository("CustomerJpaRepository")
public interface CustomerJpaRepository extends JpaRepository<Customer, String> {

	Customer findByEmail(final String email);

}
