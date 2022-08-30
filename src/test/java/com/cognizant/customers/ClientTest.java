package com.cognizant.customers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.customers.client.AuthClient;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientTest {
	
	AuthClient authClient;
	
	@Test
	void testAuthClientLoad() {
		assertThat(authClient).isNull();
	}

}
