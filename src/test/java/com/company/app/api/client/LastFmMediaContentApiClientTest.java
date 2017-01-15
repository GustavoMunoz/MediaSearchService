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

import com.company.app.api.client.LastFmMediaContentApiClient;
import com.company.app.criteria.query.QueryCriteria;
import com.company.app.criteria.query.QueryCriteria.QueryCriteriaBuilder;
import com.company.app.criteria.response.AbstractMediaResponseItem;
import com.company.app.criteria.response.MediaResponse;
import com.company.app.criteria.response.MusicMediaResponseItem;

/**
 * Unit test for {@link LastFmMediaContentApiClient}.
 * 
 * @author Gustavo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LastFmMediaContentApiClientTest {

	private static final String RESPONSE_WITH_RESULT = "{\"results\":{\"opensearch:Query\":{\"#text\":\"\",\"role\":\"request\",\"searchTerms\":\"believe\",\"startPage\":\"1\"},\"opensearch:totalResults\":\"105140\",\"opensearch:startIndex\":\"0\",\"opensearch:itemsPerPage\":\"50\",\"albummatches\":{\"album\":[{\"name\":\"Believe\",\"artist\":\"Disturbed\",\"url\":\"https://www.last.fm/music/Disturbed/Believe\",\"image\":[{\"#text\":\"https://lastfm-img2.akamaized.net/i/u/34s/bca3b80481394e25b03f4fc77c338897.png\",\"size\":\"small\"},{\"#text\":\"https://lastfm-img2.akamaized.net/i/u/64s/bca3b80481394e25b03f4fc77c338897.png\",\"size\":\"medium\"},{\"#text\":\"https://lastfm-img2.akamaized.net/i/u/174s/bca3b80481394e25b03f4fc77c338897.png\",\"size\":\"large\"},{\"#text\":\"https://lastfm-img2.akamaized.net/i/u/300x300/bca3b80481394e25b03f4fc77c338897.png\",\"size\":\"extralarge\"}],\"streamable\":\"0\",\"mbid\":\"c559efc2-f734-41ae-93bd-2d78414e0356\"}]}}}";

	private static final String RESPONSE_WITHOUT_RESULT = "{\"results\":{\"opensearch:Query\":{\"#text\":\"\",\"role\":\"request\",\"searchTerms\":\"beliejjjjjjjjjjjjjve\",\"startPage\":\"1\"},\"opensearch:totalResults\":\"0\",\"opensearch:startIndex\":\"0\",\"opensearch:itemsPerPage\":\"50\",\"albummatches\":{\"album\":[]},\"@attr\":{\"for\":\"beliejjjjjjjjjjjjjve\"}}}";

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private LastFmMediaContentApiClient lastFmMediaContentApiClient;

	@Before
	public void init() {
		ReflectionTestUtils.setField(lastFmMediaContentApiClient, "apiQueryString", "http://someapi");
	}

	@Test
	public void whenQueryCriteriaIsSupportedReturnsTrue() {
		// Given
		final QueryCriteria queryCriteria = buildMusicQueryCriteria();

		// When
		final Boolean actualComparison = lastFmMediaContentApiClient.isQueryCriteriaSupportedByApi(queryCriteria);

		// Then
		Assert.assertTrue(actualComparison);
	}

	@Test
	public void whenQueryCriteriaIsNotSupportedReturnsFalse() {
		// Given
		final QueryCriteria queryCriteria = buildMovieQueryCriteria();

		// When
		final Boolean actualComparison = lastFmMediaContentApiClient.isQueryCriteriaSupportedByApi(queryCriteria);

		// Then
		Assert.assertFalse(actualComparison);
	}

	@Test
	public void whenMovieDataIsReceivedMediaResponseIsPopulated() {
		// Given
		final QueryCriteria queryCriteria = buildMusicQueryCriteria();
		Mockito.when(restTemplate.getForObject(Mockito.any(URI.class), Mockito.any())).thenReturn(RESPONSE_WITH_RESULT);

		// When
		final MediaResponse mediaResponse = lastFmMediaContentApiClient.getMediaContent(queryCriteria);

		// Then
		Assert.assertNotNull(mediaResponse);

		final List<AbstractMediaResponseItem> mediaResponseItemList = mediaResponse.getMediaResponseItemList();
		Assert.assertNotNull(mediaResponseItemList);
		Assert.assertEquals(1, mediaResponseItemList.size());

		final AbstractMediaResponseItem abstractMediaResponseItem = mediaResponseItemList.get(0);
		Assert.assertNotNull(abstractMediaResponseItem);
		Assert.assertTrue(abstractMediaResponseItem instanceof MusicMediaResponseItem);

		final MusicMediaResponseItem musicMediaResponseItem = (MusicMediaResponseItem) abstractMediaResponseItem;
		Assert.assertEquals("Believe", musicMediaResponseItem.getTitle());
		Assert.assertEquals("Disturbed", musicMediaResponseItem.getArtist());
		Assert.assertEquals("Unknown", musicMediaResponseItem.getYear());
	}

	@Test
	public void whenNoMovieDataIsReceivedEmptyMediaResponseIsReturned() {
		// Given
		final QueryCriteria queryCriteria = buildMusicQueryCriteria();
		Mockito.when(restTemplate.getForObject(Mockito.any(URI.class), Mockito.any())).thenReturn(RESPONSE_WITHOUT_RESULT);

		// When
		final MediaResponse mediaResponse = lastFmMediaContentApiClient.getMediaContent(queryCriteria);

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
