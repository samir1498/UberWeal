package com.samir.uberweal.core.domain.exceptions;

import java.io.Serial;

public class InsufficientFundsException extends RuntimeException {

    /**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public InsufficientFundsException(String message) {
        super(message);
    }
}
