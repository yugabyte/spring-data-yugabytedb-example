package com.example.spring.jpa.springdatajpaexample.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring.jpa.springdatajpaexample.domain.Customer;

@Repository("CustomerJpaRepository")
public interface CustomerJpaRepository extends JpaRepository<Customer, String> {

	Customer findByEmail(final String email);

}
