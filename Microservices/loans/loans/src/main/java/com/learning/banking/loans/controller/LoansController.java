package com.learning.banking.loans.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learning.banking.loans.config.LoansConfig;
import com.learning.banking.loans.model.Customer;
import com.learning.banking.loans.model.Loans;
import com.learning.banking.loans.repository.LoansRepository;

@RestController
public class LoansController {
	private static final Logger logger = LoggerFactory.getLogger(LoansController.class);
	@Autowired
	private LoansRepository loansRepository;

	@Autowired
	private LoansConfig loansConfig;

	@PostMapping("/myLoans")
	public List<Loans> getLoansDetails(@RequestHeader("bank-correlation-id") String correlationid,
			@RequestBody Customer customer) {
		logger.info("getLoansDetails() method started");
		List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		// System.out.println("Invoking Loans service in retry mechanism");
		if (loans != null) {
			return loans;
		} else {
			return null;
		}
	}

	@GetMapping("/loans/properties")
	public String getPropertyDetails() throws JsonProcessingException {
		logger.info("getPropertyDetails() method started");
		return loansConfig.toString();
	}
}