package com.mango.customer.infrastructure.exception;

public class SloganRegisterException extends RuntimeException {

	public SloganRegisterException(final String identifier) {
		super(String.format("An error occurred while registering slogan for user %s.", identifier));
	}
}
