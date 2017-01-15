package com.company.app.exception;

/**
 * Exception thrown when building the {@link QueryCriteria} instance because of
 * an invalid internal state.
 * 
 * @author Gustavo
 *
 */
public class InvalidQueryCriteriaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidQueryCriteriaException(final String errorMessage) {
		super(errorMessage);
	}
}
