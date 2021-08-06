package com.example.yugabytedb.springdataexample.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.yugabytedb.springdataexample.domain.Customer;

@Repository("CustomerJpaRepository")
public interface CustomerJpaRepository extends PagingAndSortingRepository<Customer, String> {

	Customer findByEmail(final String email);

}
