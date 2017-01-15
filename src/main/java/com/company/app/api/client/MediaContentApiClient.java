package com.company.app.api.client;

import com.company.app.criteria.query.QueryCriteria;
import com.company.app.criteria.response.MediaResponse;

/**
 * Interface for all Media Content Api clients.
 * 
 * @author Gustavo
 *
 */
public interface MediaContentApiClient {

	/**
	 * Validates if the given {@link QueryCriteria} is supported by the Api.
	 * 
	 * @param queryCriteria query criteria 
	 * @return 
	 */
	boolean isQueryCriteriaSupportedByApi(QueryCriteria queryCriteria);

	/**
	 * Returns a {@link MediaReponse} with all the information returned with the given {@link QueryCriteria}
	 * 
	 * @param queryCriteria query criteria
	 * @return raw response from the media content api
	 */
	 MediaResponse getMediaContent(QueryCriteria queryCriteria);

}
