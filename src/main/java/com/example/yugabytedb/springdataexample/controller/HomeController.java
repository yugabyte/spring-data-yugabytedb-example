package com.example.yugabytedb.springdataexample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.yugabytedb.springdataexample.domain.Customer;
import com.example.yugabytedb.springdataexample.repo.CustomerYsqlRepository;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;

@RestController
public class HomeController {

	private static int PAGE_SIZE = 20;

	@Autowired
	private CustomerYsqlRepository customerRepository;

	private Fairy fairy = Fairy.create();

	@RequestMapping(method = RequestMethod.GET, path = "/loaddb")
	@ResponseBody
	public String loadDB(@RequestParam(value = "amount", required = false) String amountParam) throws Exception {

		int amount = 10;
		if (amountParam != null) {
			amount = Integer.parseInt(amountParam);
		}

		for (int i = 0; i < amount; i++) {
			Person person = fairy.person();
			Customer customer = new Customer(person.passportNumber(),
			                                 person.fullName(),
			                                 person.email(),
			                                 person.getAddress().toString(),
			                                 person.dateOfBirth().toString());
			customerRepository.save(customer);
			
		}

		return String.format("%d new customers saved into the Database.\n Current customer count: %d.",
		                     amount,
		                     customerRepository.count());
	}

	@RequestMapping(method = RequestMethod.GET, path = "/showdb")
	@ResponseBody
	public String showDB(@RequestParam(value = "page", required = false) String pageArg) throws Exception {
		StringBuilder result = new StringBuilder();
		int page = 0;
		if (pageArg != null) {
			page = Integer.parseInt(pageArg);
		}

		customerRepository.findAll(PageRequest.of(page, PAGE_SIZE))
		                     .forEach(item -> result.append(item).append("<br/>"));

		result.append("<hr/>");

		long num_entries = customerRepository.count();
		long start = page * PAGE_SIZE + 1;
		long end = Long.min(page * PAGE_SIZE + PAGE_SIZE, num_entries);

		result.append(String.format("Showing results %d-%d out of %d.\n",
		                            start,
		                            end,
		                            num_entries));
		result.append("<br/>\n");

		if (page > 0) {
			result.append("<a href=\"?page=").append(page - 1).append("\">&laquo; Previous</a>\n");
		} else {
			result.append("&laquo; Previous\n");
		}

		if (end < num_entries) {
			result.append("<a href=\"?page=").append(page + 1).append("\">Next &raquo;</a>\n");
		} else {
			result.append("Next &raquo;\n");
		}

		return result.toString();
	}

}
