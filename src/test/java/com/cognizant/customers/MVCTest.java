package com.cognizant.customers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cognizant.customers.dto.CustomerDTO;
import com.google.gson.Gson;

@SpringBootTest(classes = {CustomersMicroserviceApplication.class,Configuration.class})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("Test")
public class MVCTest {
	
	@Autowired
	MockMvc mockMvc;
	
	//token which is valid for 2hrs
	//before deploying update the same
	private String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0cmlxdWV0cngiLCJleHAiOjE2NjE3ODY2MzksImlhdCI6MTY2MTc2ODYzOX0.V2LnZQ4k8S4GQ85nKsz54_oBt2IJL1T3bTXbQupdRqvZUzBzH_uI67_U0wkqxPP7ezOWn_jCQi3CtJR8RGNiZg";
	
	
//	@Test
//	public void createCustomer() {
//		Gson gson = new Gson();
//		CustomerDTO dto = new CustomerDTO("Test User Mock", "Address 1", "Address 2", "AH09876009A", 876890091, "test@mock.mvc", new Date(), "mockmvcTest");
//		String json = gson.toJson(dto);
//		mockMvc.perform(post("create-customer"))
//	}
//	
}
