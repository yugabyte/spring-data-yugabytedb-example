package com.example.spring.jpa.springdatajpaexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.jpa.springdatajpaexample.domain.Customer;
import com.example.spring.jpa.springdatajpaexample.repo.CustomerJpaRepository;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

@RestController
public class HomeController {
	
	
	@Autowired
	CustomerJpaRepository jpaCustomerRepository;
	
	Fairy fairy = Fairy.create();
	
	@RequestMapping(method = RequestMethod.GET, path = "/loaddb")
	@ResponseBody
	public String loadDB(@RequestParam(value = "amount", required = true) String amount) throws Exception {

		Integer num = Integer.parseInt(amount);

		for (int i=0; i<num; i++) {
			Person person = fairy.person();
			Customer customer = new Customer(person.passportNumber(), person.fullName(), person.email(), person.getAddress().toString(), person.dateOfBirth().toString());
			jpaCustomerRepository.save(customer);
		}

		return "New customers successfully saved into Database";
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET, path = "/showdb")
	@ResponseBody
	public String showDB() throws Exception {
		StringBuilder result = new StringBuilder();
		Pageable topTen = new PageRequest(0, 10);

		jpaCustomerRepository.findAll(topTen).forEach(item->result.append(item+"<br/>"));

		return "First 10 customers are show here: <br/>" + result.toString();
	}

}
