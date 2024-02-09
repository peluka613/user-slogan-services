package com.mango.customer.infrastructure.exception;

public class UserRegisterException extends RuntimeException {

	public UserRegisterException(final String identifier) {
		super(String.format("An error occurred while registering user %s.", identifier));
	}
}
