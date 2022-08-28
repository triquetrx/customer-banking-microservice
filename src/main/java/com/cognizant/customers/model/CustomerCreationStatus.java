package com.cognizant.customers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @AllArgsConstructor @NoArgsConstructor class CustomerCreationStatus {
	
	private String message;
	private String customerId;

}
