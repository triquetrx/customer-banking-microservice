package com.cognizant.customers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.customers.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

	List<Customer> findByIsAccountCreated(boolean isAccountCreated);
	List<Customer> findByUsername(String username);
	Optional<Customer> findByPanNo(String panNo);
	
}
