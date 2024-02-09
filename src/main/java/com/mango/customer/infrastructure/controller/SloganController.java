package com.mango.customer.infrastructure.controller;

import com.mango.customer.application.model.SloganDTO;
import com.mango.customer.application.service.SloganService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/slogan")
public class SloganController {

	private final SloganService service;

	@PostMapping()
	public ResponseEntity<SloganDTO> createUser(@Validated @RequestBody SloganDTO slogan) {
		SloganDTO sloganResponse = service.createSlogan(slogan);
		return ResponseEntity.status(HttpStatus.CREATED).body(sloganResponse);
	}
}
