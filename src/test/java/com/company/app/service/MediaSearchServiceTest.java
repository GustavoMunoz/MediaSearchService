package com.company.app.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.company.app.api.client.MediaContentApiClient;
import com.company.app.api.client.MediaContentApiClientFactory;
import com.company.app.criteria.query.QueryCriteria;
import com.company.app.criteria.query.QueryCriteria.QueryCriteriaBuilder;
import com.company.app.criteria.response.MediaResponse;
import com.company.app.criteria.response.MovieMediaResponseItem;
import com.company.app.exception.UnsupportedQueryCriteriaForApiException;
import com.company.app.service.MediaSearchService;


/**
 * Unit test for {@link MediaSearchService}.
 * 
 * @author Gustavo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MediaSearchServiceTest {

	private final QueryCriteria queryCriteria = buildQueryCriteria();

	@Mock
	private MediaContentApiClientFactory mediaContentApiClientFactory;

	@Mock
	private MediaContentApiClient mediaContentApiClient;

	@InjectMocks
	private MediaSearchService mediaSearchService;

	@Before
	public void init() {
		Mockito.when(mediaContentApiClientFactory.getMediaContentApiClient(queryCriteria.getApi()))
				.thenReturn(mediaContentApiClient);
	}

	@Test(expected = UnsupportedQueryCriteriaForApiException.class)
	public void whenTheCriteriaDoesNotMatchWithTheApiThrowsException() {
		// Given
		Mockito.when(mediaContentApiClient.isQueryCriteriaSupportedByApi(queryCriteria)).thenReturn(false);

		// When
		mediaSearchService.search(queryCriteria);

	}
	
	@Test
	public void whenTheCriteriaMatchesAMediaResponseIsReturned() {
		// Given
		Mockito.when(mediaContentApiClient.isQueryCriteriaSupportedByApi(queryCriteria)).thenReturn(true);
		final MediaResponse expectedMediaResponse = buildMediaResponse(); 
		Mockito.when(mediaContentApiClient.getMediaContent(queryCriteria)).thenReturn(expectedMediaResponse);

		// When
		final MediaResponse actualMediaResponse = mediaSearchService.search(queryCriteria);
		
		// Then
		Assert.assertEquals(expectedMediaResponse, actualMediaResponse);
	}

	////////// HELPERS
	private QueryCriteria buildQueryCriteria() {
		return new QueryCriteriaBuilder().withApi("api").withMovie("movie").build();
	}
	
	private MediaResponse buildMediaResponse() {
		final MediaResponse mediaResponse = new MediaResponse();
		
		final MovieMediaResponseItem movieMediaResponseItem = new MovieMediaResponseItem();
		movieMediaResponseItem.setTitle("title");
		movieMediaResponseItem.setDirector("director");
		movieMediaResponseItem.setYear("year");
		
		mediaResponse.addMediaResponseItem(movieMediaResponseItem);
		
		return mediaResponse;
	}
}
