package com.mango.customer.infrastructure.exception;

public class MaxAllowedSlogansReachedException extends RuntimeException {
	public MaxAllowedSlogansReachedException(final String identifier, final Integer maxSlogansAllowed) {
		super(String.format("User %s reached the max of %s slogans.", identifier, maxSlogansAllowed));
	}
}
