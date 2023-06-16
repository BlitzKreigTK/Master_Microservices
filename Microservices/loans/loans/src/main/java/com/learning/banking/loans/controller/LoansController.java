package com.learning.banking.loans.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learning.banking.loans.config.LoansConfig;
import com.learning.banking.loans.model.Customer;
import com.learning.banking.loans.model.Loans;
import com.learning.banking.loans.repository.LoansRepository;

@RestController
public class LoansController {

	@Autowired
	private LoansRepository loansRepository;

	@Autowired
	private LoansConfig loansConfig;

	@PostMapping("/myLoans")
	public List<Loans> getLoansDetails(@RequestBody Customer customer) {
		List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		if (loans != null) {
			return loans;
		} else {
			return null;
		}
	}

	@GetMapping("/loans/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		return loansConfig.toString();
	}
}