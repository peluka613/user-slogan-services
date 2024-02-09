package com.mango.customer.infrastructure.exception.model;

import lombok.Builder;

@Builder
public class ErrorResponse {
	private Integer code;
	private String message;
}
