package com.cognizant.customers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cognizant.customers.dto.CustomerDTO;
import com.cognizant.customers.exception.AccountExistsException;
import com.cognizant.customers.exception.CustomerDoesnotExistsException;
import com.cognizant.customers.exception.InvalidAccessException;
import com.cognizant.customers.exception.UserLoginCreationException;
import com.cognizant.customers.model.Customer;

@Service
public interface CustomerService {

	String addNewCustomer(String token, CustomerDTO customerDTO)
			throws UserLoginCreationException, InvalidAccessException, AccountExistsException;

	List<Customer> getAllCustomer(String token) throws InvalidAccessException;

	Customer getCustomer(String token, String id) throws InvalidAccessException, CustomerDoesnotExistsException;

	void markAccountCreated(String token, String id);

	Optional<Customer> checkForExistingAccount(String panNo, String token) throws InvalidAccessException;

	List<Customer> getCustomersWithoutAccount(String token) throws InvalidAccessException;

	
}
