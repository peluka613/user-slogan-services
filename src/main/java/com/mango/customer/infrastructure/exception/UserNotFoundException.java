package com.mango.customer.infrastructure.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(final String identifier) {
		super(String.format("User with identifier %s not found.", identifier));
	}
}

