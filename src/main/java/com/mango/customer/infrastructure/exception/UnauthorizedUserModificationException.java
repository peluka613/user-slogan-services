package com.mango.customer.infrastructure.exception;

public class UnauthorizedUserModificationException extends RuntimeException {
	public UnauthorizedUserModificationException(final String identifier) {
		super(String.format("User %s not allowed to edit user details.", identifier));
	}
}
