package com.mango.customer.infrastructure.exception;

import com.mango.customer.infrastructure.exception.model.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {
	@ExceptionHandler({UserRegisterException.class, ConstraintViolationException.class, DataIntegrityViolationException.class})
	public ResponseEntity<ErrorResponse> handleUserRegisterException(RuntimeException ex) {
		return createResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY, ex);
	}

	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
		return createResponseEntity(HttpStatus.NO_CONTENT, ex);
	}

	@ExceptionHandler({UnauthorizedUserModificationException.class})
	public ResponseEntity<ErrorResponse> handleUnauthorizedUserModificationException(UnauthorizedUserModificationException ex) {
		return createResponseEntity(HttpStatus.UNAUTHORIZED, ex);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex);
	}

	private ResponseEntity<ErrorResponse> createResponseEntity(HttpStatus httpStatus, Exception ex) {
		ErrorResponse errorResponse = ErrorResponse.builder()
			.code(httpStatus.value())
			.message(ex.getLocalizedMessage())
			.build();
		return new ResponseEntity<>(errorResponse, httpStatus);
	}
}
