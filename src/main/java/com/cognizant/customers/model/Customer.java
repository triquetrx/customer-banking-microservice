package com.cognizant.customers.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public @Data @AllArgsConstructor @NoArgsConstructor class Customer {
	
	@Id
	private String customerId;
	private String name;
	private String addressLine1;
	private String addressLine2;
	private String panNo;
	private Date dateOfBirth;
	private long phoneNumber;
	private String email;
	private String username;
	private boolean isAccountCreated;

}
