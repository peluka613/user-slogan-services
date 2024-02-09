package com.mango.customer.infrastructure.exception.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
	private Integer code;
	private String message;
}
