package com.mango.customer.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mango.customer.application.model.UserDTO;
import com.mango.customer.application.service.UserService;
import com.mango.customer.infrastructure.exception.UnauthorizedUserModificationException;
import com.mango.customer.infrastructure.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		UserDTO user = getUserDTO("John");

		when(userService.createUser(any(UserDTO.class))).thenReturn(user);
		when(userService.findUserByEmail(any(String.class))).thenReturn(user);
	}

	@Test
	void createUser_Success() throws Exception {
		UserDTO newUser = getUserDTO("John");

		mockMvc.perform(MockMvcRequestBuilders.post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newUser)))
			.andExpect(status().isCreated())
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"));
	}

	private static UserDTO getUserDTO(String name) {
		UserDTO newUser = new UserDTO();
		newUser.setName(name);
		newUser.setLastname("Doe");
		newUser.setEmail("john.doe@example.com");
		newUser.setAddress("123 Main St");
		newUser.setCity("Anytown");
		return newUser;
	}

	@Test
	void findUserByEmail_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/{email}", "john.doe@example.com")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"));
	}

	@Test
	void editUser_Success() throws Exception {
		UserDTO updatedUser = getUserDTO("Jane");

		when(userService.updateUser(any(UserDTO.class), any(String.class))).thenReturn(updatedUser);

		mockMvc.perform(MockMvcRequestBuilders.put("/user/{email}", "john.doe@example.com")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedUser)))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jane"));
	}

	@Test
	void createUser_ConstraintViolationException() throws Exception {
		UserDTO newUser = getUserDTO("John");
		when(userService.createUser(any(UserDTO.class))).thenThrow(new DataIntegrityViolationException("Constraint violation"));

		mockMvc.perform(MockMvcRequestBuilders.post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newUser)))
			.andExpect(status().isUnprocessableEntity())
			.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.UNPROCESSABLE_ENTITY.value()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(containsString("Constraint violation")));
	}

	@Test
	void findUserByEmail_NotFound() throws Exception {
		when(userService.findUserByEmail(any(String.class))).thenThrow(new UserNotFoundException("User not found"));

		mockMvc.perform(MockMvcRequestBuilders.get("/user/{email}", "nonexistent@example.com")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}

	@Test
	void editUser_UnauthorizedUserModificationException() throws Exception {
		UserDTO updatedUser = getUserDTO("John");
		when(userService.updateUser(any(UserDTO.class), any(String.class))).thenThrow(new UnauthorizedUserModificationException("Unauthorized modification"));

		mockMvc.perform(MockMvcRequestBuilders.put("/user/{email}", "john.doe@example.com")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedUser)))
			.andExpect(status().isUnauthorized())
			.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.UNAUTHORIZED.value()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(containsString("Unauthorized modification")));
	}

	@Test
	void genericException() throws Exception {
		UserDTO newUser = getUserDTO("John");
		when(userService.createUser(any(UserDTO.class))).thenThrow(new RuntimeException("Generic error"));

		mockMvc.perform(MockMvcRequestBuilders.post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newUser)))
			.andExpect(status().isInternalServerError())
			.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.message").value(containsString("Generic error")));
	}
}
