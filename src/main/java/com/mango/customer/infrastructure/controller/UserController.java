package com.mango.customer.infrastructure.controller;

import com.mango.customer.application.model.UserDTO;
import com.mango.customer.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	// It is just a simple guide to start, you may create the controllers as you wish.
	// Feel free modifying all stuff implemented here, for instance requested/returned objects.

	private final UserService service;

	@PostMapping()
	public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserDTO user) {
		UserDTO userResponse = service.createUser(user);
		return ResponseEntity.ok(userResponse);
	}
}
