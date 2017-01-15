package com.company.app.api.client;

import java.net.URI;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.company.app.api.client.ImdbMediaContentApiClient;
import com.company.app.criteria.query.QueryCriteria;
import com.company.app.criteria.query.QueryCriteria.QueryCriteriaBuilder;
import com.company.app.criteria.response.AbstractMediaResponseItem;
import com.company.app.criteria.response.MediaResponse;
import com.company.app.criteria.response.MovieMediaResponseItem;

/**
 * Unit test for {@link ImdbMediaContentApiClient}.
 * 
 * @author Gustavo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ImdbMediaContentApiClientTest {

	private static final String RESPONSE_WITH_RESULT = "{ \"title_popular\":[{ \"id\":\"tt0082971\", \"title\":\"Raiders of the Lost Ark\", \"name\":\"\", \"title_description\":\"1981, Steven Spielberg\", \"episode_title\":\"\", \"description\":\"1981, <a href=>Steven Spielberg<a>\"}]}";

	private static final String RESPONSE_WITHOUT_RESULT = "{ }";

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private ImdbMediaContentApiClient imdbMediaContentApiClient;

	@Before
	public void init() {
		ReflectionTestUtils.setField(imdbMediaContentApiClient, "apiQueryString", "http://someapi");
	}

	@Test
	public void whenQueryCriteriaIsSupportedReturnsTrue() {
		// Given
		final QueryCriteria queryCriteria = buildMovieQueryCriteria();
		
		// 
		final Boolean actualComparison = imdbMediaContentApiClient.isQueryCriteriaSupportedByApi(queryCriteria);
		
		//
		Assert.assertTrue(actualComparison);
	}
	
	@Test
	public void whenQueryCriteriaIsNotSupportedReturnsFalse() {
		// Given
		final QueryCriteria queryCriteria = buildMusicQueryCriteria();
		
		// 
		final Boolean actualComparison = imdbMediaContentApiClient.isQueryCriteriaSupportedByApi(queryCriteria);
		
		//
		Assert.assertFalse(actualComparison);
	}
	
	@Test
	public void whenMovieDataIsReceivedMediaResponseIsPopulated() {
		// Given
		final QueryCriteria queryCriteria = buildMovieQueryCriteria();
		Mockito.when(restTemplate.getForObject(Mockito.any(URI.class), Mockito.any())).thenReturn(RESPONSE_WITH_RESULT);

		// When
		final MediaResponse mediaResponse = imdbMediaContentApiClient.getMediaContent(queryCriteria);

		// Then
		Assert.assertNotNull(mediaResponse);

		final List<AbstractMediaResponseItem> mediaResponseItemList = mediaResponse.getMediaResponseItemList();
		Assert.assertNotNull(mediaResponseItemList);
		Assert.assertEquals(1, mediaResponseItemList.size());

		final AbstractMediaResponseItem abstractMediaResponseItem = mediaResponseItemList.get(0);
		Assert.assertNotNull(abstractMediaResponseItem);
		Assert.assertTrue(abstractMediaResponseItem instanceof MovieMediaResponseItem);

		final MovieMediaResponseItem movieMediaResponseItem = (MovieMediaResponseItem) abstractMediaResponseItem;
		Assert.assertEquals("Raiders of the Lost Ark", movieMediaResponseItem.getTitle());
		Assert.assertEquals("Steven Spielberg", movieMediaResponseItem.getDirector());
		Assert.assertEquals("1981", movieMediaResponseItem.getYear());
	}

	@Test
	public void whenNoMovieDataIsReceivedEmptyMediaResponseIsReturned() {
		// Given
		final QueryCriteria queryCriteria = buildMovieQueryCriteria();
		Mockito.when(restTemplate.getForObject(Mockito.any(URI.class), Mockito.any())).thenReturn(RESPONSE_WITHOUT_RESULT);

		// When
		final MediaResponse mediaResponse = imdbMediaContentApiClient.getMediaContent(queryCriteria);

		// Then
		Assert.assertNotNull(mediaResponse);
		
		final List<AbstractMediaResponseItem> mediaResponseItemList = mediaResponse.getMediaResponseItemList();
		Assert.assertNotNull(mediaResponseItemList);
		Assert.assertEquals(0, mediaResponseItemList.size());

	}

	/////////// HELPERS
	private QueryCriteria buildMovieQueryCriteria() {
		return new QueryCriteriaBuilder().withApi("api").withMovie("movie").build();
	}
	
	private QueryCriteria buildMusicQueryCriteria() {
		return new QueryCriteriaBuilder().withApi("api").withMusic("music").build();
	}
}
