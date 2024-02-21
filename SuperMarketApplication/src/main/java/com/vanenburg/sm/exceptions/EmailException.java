package com.vanenburg.sm.exceptions;

import lombok.Getter;

@Getter
public class EmailException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String emailError;

	public EmailException(String emailError) {
		this.emailError = emailError;
	}
}
