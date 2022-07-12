package com.deane.urlapp.controller;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.deane.urlapp.dao.WebAddressRepo;
import com.deane.urlapp.model.WebAddress;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestWebAddressController {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebAddressRepo webAddressRepo;

	@Test
	public void testWebAddressPost() throws Exception {

		LocalDateTime now = LocalDateTime.now().minusSeconds(1);
		mockMvc.perform(MockMvcRequestBuilders.post("/address").contentType("application/json")
				.content("{\"originalAddress\" : \"www.testwedaddress.com\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk());

		WebAddress wba = webAddressRepo.findByShortAddress("etssapeett");
		Assertions.assertTrue(wba.getCreatedDate().isAfter(now));
		webAddressRepo.delete(wba);
	}

	@Test
	public void testWebAddressPostFailDueValidUrl() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/address").contentType("application/json")
				.content("{\"originalAddress\" : \"teacups\"}"))
				.andExpect(MockMvcResultMatchers.status().isIAmATeapot());

	}

	@Test
	public void testWebAddressGetWithShortAddress() throws Exception {

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/eetseptcpt").contentType(MediaType.APPLICATION_JSON)).andReturn();

		Assertions.assertTrue(result.getResponse().getRedirectedUrl().contains("http://www.deaniscool.com"));

	}
	
	@Test
	public void testWebAddressGetWithIncorrectShortAddress() throws Exception{
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/dean").contentType(MediaType.APPLICATION_JSON)).andReturn();

		Assertions.assertTrue(result.getResponse().getStatus() == 418);

	}

}
