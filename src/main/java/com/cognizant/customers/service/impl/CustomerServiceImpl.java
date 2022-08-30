package com.cognizant.customers.service.impl;

import java.util.List;
import java.util.Optional;

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
	public String addNewCustomer(String token, CustomerDTO customerDTO)
			throws UserLoginCreationException, InvalidAccessException, AccountExistsException {
		String customerId = "CUST" + repository.count();
		if (authClient.validatingToken(token).isValidStatus()) {
			if (authClient.validatingToken(token).getUserRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
				if (!checkForExistingAccount(customerDTO.getPanNo(),token) && !userNameExists(customerDTO.getUsername())) {
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
	public List<Customer> getAllCustomer(String token) throws InvalidAccessException {
		if (authClient.validatingToken(token).isValidStatus()
				&& authClient.validatingToken(token).getUserRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
			return repository.findAll();
		}
		throw new InvalidAccessException();
	}

	@Override
	public List<Customer> getCustomersWithoutAccount(String token) throws InvalidAccessException {
		if (authClient.validatingToken(token).isValidStatus()
				&& authClient.validatingToken(token).getUserRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
			return repository.findByIsAccountCreated(false);
		}
		throw new InvalidAccessException();
	}

	@Override
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
	public void markAccountCreated(String token, String id) {
		if (authClient.validatingToken(token).isValidStatus()) {
			Customer customer = repository.findById(id).get();
			customer.setAccountCreated(true);
			repository.save(customer);
		}
	}

	@Override
	public boolean checkForExistingAccount(String panNo,String token) {
		if(authClient.validatingToken(token).isValidStatus()) {			
			List<Customer> findByPanNo = repository.findByPanNo(panNo);
			if (findByPanNo.isEmpty()) {
				return false;
			}
			return true;
		}
		return false;
	}

	private boolean userNameExists(String username) {
		List<Customer> findByUsername = repository.findByUsername(username);
		if (findByUsername.isEmpty()) {
			return false;
		}
		return true;
	}


}
