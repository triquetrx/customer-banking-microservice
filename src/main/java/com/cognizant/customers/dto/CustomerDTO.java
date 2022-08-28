package com.cognizant.customers.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @AllArgsConstructor @NoArgsConstructor class CustomerDTO {
	
	private String name;
	private String addressLine1;
	private String addressLine2;
	private String panNo;
	private long phoneNumber;
	private String email;
	private Date dateOfBirth;
	private String username;

}
