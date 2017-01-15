package com.company.app.exception;

/**
 * Exception used when the values within the given {@link QueryCriteria} are not
 * applicable to the specific {@link MediaContentApiClient}.
 * 
 * @author Gustavo
 *
 */
public class UnsupportedQueryCriteriaForApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnsupportedQueryCriteriaForApiException(final String errorMessage) {
		super(errorMessage);
	}

}
