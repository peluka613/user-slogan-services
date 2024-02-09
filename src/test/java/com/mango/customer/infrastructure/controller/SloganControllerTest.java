package com.mango.customer.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mango.customer.application.model.SloganDTO;
import com.mango.customer.application.service.SloganService;
import com.mango.customer.infrastructure.exception.MaxAllowedSlogansReachedException;
import com.mango.customer.infrastructure.exception.SloganRegisterException;
import com.mango.customer.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SloganController.class)
class SloganControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SloganService sloganService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		SloganDTO slogan = getSloganDTO();

		when(sloganService.createSlogan(any(SloganDTO.class))).thenReturn(slogan);
	}

	@Test
	void createSlogan_Success() throws Exception {
		SloganDTO newSlogan = getSloganDTO();

		mockMvc.perform(MockMvcRequestBuilders.post("/slogan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newSlogan)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.slogan").value("Slogan test"));
	}

	@Test
	void createSlogan_SloganRegisterException() throws Exception {
		when(sloganService.createSlogan(any(SloganDTO.class))).thenThrow(new SloganRegisterException("Registration error"));

		SloganDTO newSlogan = getSloganDTO();
		newSlogan.setEmail("john.doe@example.com");

		mockMvc.perform(MockMvcRequestBuilders.post("/slogan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newSlogan)))
			.andExpect(status().isUnprocessableEntity());
	}

	@Test
	void createSlogan_MaxAllowedSlogansReachedException() throws Exception {
		when(sloganService.createSlogan(any(SloganDTO.class)))
			.thenThrow(new MaxAllowedSlogansReachedException("john.doe@example.com", 3));

		SloganDTO newSlogan = getSloganDTO();

		mockMvc.perform(MockMvcRequestBuilders.post("/slogan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newSlogan)))
			.andExpect(status().isUnprocessableEntity())
			.andExpect(jsonPath("$.message").value("User john.doe@example.com reached the max of 3 slogans."));
	}

	@Test
	void createSlogan_UserNotFoundException() throws Exception {
		when(sloganService.createSlogan(any(SloganDTO.class))).thenThrow(new UserNotFoundException("User not found"));

		SloganDTO newSlogan = getSloganDTO();

		mockMvc.perform(MockMvcRequestBuilders.post("/slogan")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newSlogan)))
			.andExpect(status().isNoContent()); // Assuming NO_CONTENT for user not found
	}

	private static SloganDTO getSloganDTO() {
		SloganDTO newSlogan = new SloganDTO();
		newSlogan.setSlogan("Slogan test");
		newSlogan.setEmail("user@example.com");
		return newSlogan;
	}
}
