package com.cognizant.customers.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.customers.client.AuthClient;
import com.cognizant.customers.dto.CustomerDTO;
import com.cognizant.customers.dto.NewUserDTO;
import com.cognizant.customers.exception.AccountExistsException;
import com.cognizant.customers.exception.CustomerDoesnotExistsException;
import com.cognizant.customers.exception.InvalidAccessException;
import com.cognizant.customers.exception.UserLoginCreationException;
import com.cognizant.customers.model.Customer;
import com.cognizant.customers.repository.CustomerRepository;
import com.cognizant.customers.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	AuthClient authClient;

	@Autowired
	CustomerRepository repository;

	@Override
	@Transactional
	public String addNewCustomer(String token, CustomerDTO customerDTO)
			throws UserLoginCreationException, InvalidAccessException, AccountExistsException {
		String customerId = "CUST" + repository.count();
		if (authClient.validatingToken(token).isValidStatus()) {
			if (authClient.validatingToken(token).getUserRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
				if (checkForExistingAccount(customerDTO.getPanNo(),token).isEmpty () && !userNameExists(customerDTO.getUsername())) {
					String newUserLogin = authClient.addNewUser(new NewUserDTO(customerDTO.getName(),
							customerDTO.getUsername(), customerId + customerDTO.getName().split(" ")[0], customerId));
					if (newUserLogin.equalsIgnoreCase("New User Created")) {
						Customer customer = new Customer(customerId, customerDTO.getName(),
								customerDTO.getAddressLine1(), customerDTO.getAddressLine2(), customerDTO.getPanNo(),
								customerDTO.getDateOfBirth(), customerDTO.getPhoneNumber(), customerDTO.getEmail(),
								customerDTO.getUsername(), false);
						repository.save(customer);
						return customerId;
					}
					throw new UserLoginCreationException();
				}
				throw new AccountExistsException();
			}
			throw new InvalidAccessException();
		}
		return null;
	}

	@Override
	@Transactional
	public List<Customer> getAllCustomer(String token) throws InvalidAccessException {
		if (authClient.validatingToken(token).isValidStatus()
				&& authClient.validatingToken(token).getUserRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
			return repository.findAll();
		}
		throw new InvalidAccessException();
	}

	@Override
	@Transactional
	public List<Customer> getCustomersWithoutAccount(String token) throws InvalidAccessException {
		if (authClient.validatingToken(token).isValidStatus()
				&& authClient.validatingToken(token).getUserRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
			return repository.findByIsAccountCreated(false);
		}
		throw new InvalidAccessException();
	}

	@Override
	@Transactional
	public Customer getCustomer(String token, String id) throws InvalidAccessException, CustomerDoesnotExistsException {
		if (authClient.validatingToken(token).isValidStatus()
				&& authClient.validatingToken(token).getUserRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
			Optional<Customer> customers = repository.findById(id);
			if (!customers.isEmpty()) {
				return customers.get();
			}
			throw new CustomerDoesnotExistsException();
		}
		throw new InvalidAccessException();
	}

	@Override
	@Transactional
	public void markAccountCreated(String token, String id) {
		if (authClient.validatingToken(token).isValidStatus()) {
			Customer customer = repository.findById(id).get();
			customer.setAccountCreated(true);
			repository.save(customer);
		}
	}

	@Override
	@Transactional
	public Optional<Customer> checkForExistingAccount(String panNo,String token) throws InvalidAccessException {
		if(authClient.validatingToken(token).isValidStatus()) {			
			return repository.findByPanNo(panNo);
		}
		throw new InvalidAccessException();
	}

	@Transactional
	private boolean userNameExists(String username) {
		List<Customer> findByUsername = repository.findByUsername(username);
		if (findByUsername.isEmpty()) {
			return false;
		}
		return true;
	}


}
