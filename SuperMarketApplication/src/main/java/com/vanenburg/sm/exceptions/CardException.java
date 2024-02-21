package com.vanenburg.sm.exceptions;

import lombok.Getter;

@Getter
public class CardException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String cardError;

	public CardException(String cardError) {
		this.cardError = cardError;
	}
}
