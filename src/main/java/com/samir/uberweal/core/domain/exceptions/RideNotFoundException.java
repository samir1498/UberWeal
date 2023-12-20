package com.samir.uberweal.core.domain.exceptions;

import java.io.Serial;

public class RideNotFoundException extends RuntimeException {

    /**
	 *
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	public RideNotFoundException(String message) {
        super(message);
    }
}
