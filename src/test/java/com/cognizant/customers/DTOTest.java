package com.cognizant.customers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.customers.dto.CustomerDTO;
import com.cognizant.customers.dto.NewUserDTO;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DTOTest {

	CustomerDTO customerDTO;
	NewUserDTO newUserDTO;

	@Test
	void testCustomerDTO() {
		customerDTO = new CustomerDTO();
		CustomerDTO dto = new CustomerDTO("Test user", "address1", "address2", "AHK010101PK", 1234567890,
				"test@test.com", new Date(), "test");
		customerDTO.setName("Test user");
		customerDTO.setAddressLine1("address1");
		customerDTO.setAddressLine2("address2");
		customerDTO.setPanNo("AHK010101PK");
		customerDTO.setPhoneNumber(1234567890);
		customerDTO.setEmail("test@test.com");
		customerDTO.setDateOfBirth(new Date());
		customerDTO.setUsername("test");
		assertEquals(dto, customerDTO);
	}
	
	@Test
	void testNewUserDTO() {
		newUserDTO = new NewUserDTO("test", "test", "test", "CUST00");
		
		NewUserDTO dto = new NewUserDTO();
		dto.setCustomerId("CUST00");
		dto.setName("test");
		dto.setPassword("test");
		dto.setUsername("test");
	}

}
