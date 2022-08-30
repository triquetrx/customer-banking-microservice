package com.cognizant.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.customers.model.Customer;
import com.cognizant.customers.model.CustomerCreationStatus;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ModelTest {

	Customer customer;
	CustomerCreationStatus creationStatus;

	@Test
	void checkCustomer() {
		customer = new Customer("CUST00", "test", "address1", "address2", 
				"AHK00HI90", new Date(), 1234567890,
				"test@test.com", "test", false);
		
		assertEquals("CUST00", customer.getCustomerId());
		assertEquals("test", customer.getName());
		assertEquals("address1", customer.getAddressLine1());
		assertEquals("address2", customer.getAddressLine2());
		assertEquals(new Date(), customer.getDateOfBirth());
		assertEquals(1234567890, customer.getPhoneNumber());
		assertEquals("test@test.com", customer.getEmail());
		assertEquals("test", customer.getUsername());
		assertEquals(false, customer.isAccountCreated());
	}
	
	@Test
	void checkCreationStatus() {
		creationStatus = new CustomerCreationStatus("Created", "CUST00");
		assertEquals("Created", creationStatus.getMessage());
		assertEquals("CUST00", creationStatus.getCustomerId());
	}

}
