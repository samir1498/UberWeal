package com.samir.uberweal.domain.exceptions;

import java.io.Serial;

public class RiderNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public RiderNotFoundException(String message) {
        super(message);
    }
}
