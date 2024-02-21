package com.vanenburg.sm.exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(ApiRequestException.class)
	public ResponseEntity<?> handleApiRequestException(ApiRequestException exception) {
		return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
	}

	@ExceptionHandler(InputFieldException.class)
	public ResponseEntity<?> handleInputFieldException(InputFieldException exception) {
		return ResponseEntity.badRequest().body(exception.getErrorsMap());
	}

	@ExceptionHandler(EmailException.class)
	public ResponseEntity<?> handleEmailError(EmailException exception) {
		return ResponseEntity.badRequest().body(exception.getEmailError());
	}

	@ExceptionHandler(CardException.class)
	public ResponseEntity<?> handleCardError(CardException exception) {
		return ResponseEntity.badRequest().body(exception.getCardError());
	}

	@ExceptionHandler(JwtAuthenticationException.class)
	public ResponseEntity<?> handleJwtExceptionr(JwtAuthenticationException exception) {
		return ResponseEntity.status(exception.getStatus()).body(exception.getJwtError());
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> handleAuthenticationExceptionr(AuthenticationException exception) {
		return ResponseEntity.badRequest().body(exception.getMessage());
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File too large!");
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception exception) {
		return ResponseEntity.internalServerError().body(Map.of("INTERNAL_ERROR", exception.getMessage()));
	}
}
