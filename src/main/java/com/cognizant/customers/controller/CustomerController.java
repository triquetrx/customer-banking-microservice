package com.cognizant.customers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.customers.dto.CustomerDTO;
import com.cognizant.customers.exception.AccountExistsException;
import com.cognizant.customers.exception.CustomerDoesnotExistsException;
import com.cognizant.customers.exception.InvalidAccessException;
import com.cognizant.customers.exception.UserLoginCreationException;
import com.cognizant.customers.model.CustomerCreationStatus;
import com.cognizant.customers.service.CustomerService;

import feign.FeignException.FeignClientException;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService service;

	@PostMapping("/create-customer")
	@CrossOrigin(origins = "http://localhost:5000")
	public ResponseEntity<?> createCustomer(@RequestHeader(name = "Authorization") String token,
			@RequestBody CustomerDTO customerDTO) {
		
		try {
			String id = service.addNewCustomer(token, customerDTO);
			return new ResponseEntity<>(new CustomerCreationStatus("CREATED_NEW_CUSTOMER",id),HttpStatus.OK);
		} catch(UserLoginCreationException e) {
			return new ResponseEntity<>("ERROR_OCCURED_IN_CREATING_LOGIN_ID",HttpStatus.SERVICE_UNAVAILABLE);			
		} catch(InvalidAccessException e) {
			return new ResponseEntity<>("UNAUTHORIZED_ACCESS",HttpStatus.UNAUTHORIZED);						
		} catch(FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1]+message[0].split("")[2]+message[0].split("")[3]);
			return new ResponseEntity<>(message[5],HttpStatus.valueOf(errCode));
		} catch (AccountExistsException e) {
			return new ResponseEntity<>("ACCOUNT_ALREADY_EXISTS",HttpStatus.BAD_REQUEST);						
		}
	}
	
	@GetMapping("/get-all-customer")
	@CrossOrigin(origins = "http://localhost:5000")
	public ResponseEntity<?> getAllCustomer(@RequestHeader(name = "Authorization") String token) {
		try {
			return new ResponseEntity<>(service.getAllCustomer(token),HttpStatus.OK);	
		} catch(InvalidAccessException e) {
			return new ResponseEntity<>("UNAUTHORIZED_ACCESS",HttpStatus.UNAUTHORIZED);						
		} catch(FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1]+message[0].split("")[2]+message[0].split("")[3]);
			return new ResponseEntity<>(message[5],HttpStatus.valueOf(errCode));
		} 
	}
	
	@GetMapping("/get-customers-without-account")
	@CrossOrigin(origins = "http://localhost:5000")
	public ResponseEntity<?> getAllCustomersWithoutAccount(@RequestHeader(name = "Authorization") String token) {
		try {
			return new ResponseEntity<>(service.getCustomersWithoutAccount(token),HttpStatus.OK);	
		} catch(InvalidAccessException e) {
			return new ResponseEntity<>("UNAUTHORIZED_ACCESS",HttpStatus.UNAUTHORIZED);						
		} catch(FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1]+message[0].split("")[2]+message[0].split("")[3]);
			return new ResponseEntity<>(message[5],HttpStatus.valueOf(errCode));
		} 
	}
	
	@GetMapping("/get-customer-details/{customerId}")
	@CrossOrigin(origins = "http://localhost:5000")
	public ResponseEntity<?> getAllCustomerdetails(@RequestHeader(name = "Authorization") String token,@PathVariable String customerId) {
		try {
			return new ResponseEntity<>(service.getCustomer(token, customerId),HttpStatus.OK);	
		} catch(InvalidAccessException e) {
			return new ResponseEntity<>("UNAUTHORIZED_ACCESS",HttpStatus.UNAUTHORIZED);						
		} catch (CustomerDoesnotExistsException e) {
			return new ResponseEntity<>("CUSTOMER_DOESNOT_EXISTS",HttpStatus.BAD_REQUEST);									
		} catch(FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1]+message[0].split("")[2]+message[0].split("")[3]);
			return new ResponseEntity<>(message[5],HttpStatus.valueOf(errCode));
		} 
	}
	
	@GetMapping("/mark-account-created/{customerId}")
	@CrossOrigin(origins = "http://localhost:5000")
	public ResponseEntity<?> markAccountAsCreated(@RequestHeader(name="Authorization") String token,@PathVariable String customerId){
		try {
			service.markAccountCreated(token, customerId);
			return new ResponseEntity<>(true,HttpStatus.OK);	
		} catch(FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1]+message[0].split("")[2]+message[0].split("")[3]);
			return new ResponseEntity<>(message[5],HttpStatus.valueOf(errCode));
		} 
	}

	@GetMapping("/is-account-present/{panNumber}")
	@CrossOrigin(origins = "http://localhost:5000")
	public boolean isAccountPresent(@RequestHeader(name="Authorization")String token,@PathVariable String panNumber) {
		return service.checkForExistingAccount(panNumber, token);
	}
	
}
