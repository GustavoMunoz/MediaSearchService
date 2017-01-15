package com.company.app.exception;

/**
 * Exception thrown when there is not a specific {@link MediaContentApiClient} applicable.
 * 
 * @author Gustavo
 *
 */
public class MissingMediaContentApiClientException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public MissingMediaContentApiClientException(final String errorMessage) {
		super(errorMessage);
	}
}
