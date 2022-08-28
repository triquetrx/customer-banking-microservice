package com.cognizant.customers.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.customers.dto.NewUserDTO;
import com.cognizant.customers.dto.ValidatingDTO;

@FeignClient(name = "auth-client",url="http://localhost:8001")
public interface AuthClient {
	
	@GetMapping("/validate")
	public ValidatingDTO validatingToken(@RequestHeader(name="Authorization") String token);
	
	@PostMapping("/register")
	public String addNewUser(@RequestBody NewUserDTO newUserDTO);

}
