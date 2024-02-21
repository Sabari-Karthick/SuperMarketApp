package com.vanenburg.sm.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class JwtAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String jwtError;
	private final HttpStatus status;

	public JwtAuthenticationException(String jwtError,HttpStatus status) {
		this.jwtError = jwtError;
		this.status = status;
	}
}
