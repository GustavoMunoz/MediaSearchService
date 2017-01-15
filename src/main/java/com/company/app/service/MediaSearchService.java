package com.company.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.app.api.client.MediaContentApiClient;
import com.company.app.api.client.MediaContentApiClientFactory;
import com.company.app.criteria.query.QueryCriteria;
import com.company.app.criteria.response.MediaResponse;
import com.company.app.exception.UnsupportedQueryCriteriaForApiException;

/**
 * Media Search service that interacts with all the media content apis.
 * 
 * @author Gustavo
 *
 */
@Component
public class MediaSearchService {

	@Autowired
	private MediaContentApiClientFactory mediaContentApiClientFactory;
	
	/**
	 * Given a {@link QueryCriteria} obtains the proper {@link MediaContentApiClient} and returns its {@link MediaResponse}.
	 * 
	 * @param queryCriteria instance of {@link QueryCriteria} that holds the given search parameters.
	 * @return instance of {@link MediaResponse} holding the response.
	 * @throws UnsupportedQueryCriteriaForApiException if the given criteria is invalid.
	 */
	public MediaResponse search(final QueryCriteria queryCriteria) {
		
		final MediaContentApiClient mediaContentApiClient = mediaContentApiClientFactory.getMediaContentApiClient(queryCriteria.getApi());
		
		if (mediaContentApiClient.isQueryCriteriaSupportedByApi(queryCriteria)) {
			return mediaContentApiClient.getMediaContent(queryCriteria);
		} else {
			throw new UnsupportedQueryCriteriaForApiException("Invalid given query criteria: " + queryCriteria.toString());
		}
	}
}
