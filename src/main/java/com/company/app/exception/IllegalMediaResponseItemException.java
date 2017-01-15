package com.company.app.exception;

import com.company.app.criteria.response.AbstractMediaResponseItem;

/**
 * Exception thrown when an instance of {@link AbstractMediaResponseItem} has
 * not a valid state.
 * 
 * @author Gustavo
 *
 */
public class IllegalMediaResponseItemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalMediaResponseItemException(final String errorMessage) {
		super(errorMessage);
	}
}
