package com.mango.customer;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	// TODO: It is just a simple guide to start, you may create the controllers as you wish. Feel free modifying all stuff implemented here, for instance requested/returned objects.
	public void createUser(String user) {
		saveUser(user);
	}

	public void saveUser(String user) {
		throw new UnsupportedOperationException();
	}
}
