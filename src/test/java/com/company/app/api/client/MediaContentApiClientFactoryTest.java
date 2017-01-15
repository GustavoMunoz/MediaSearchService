package com.company.app.api.client;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.company.app.api.client.ImdbMediaContentApiClient;
import com.company.app.api.client.LastFmMediaContentApiClient;
import com.company.app.api.client.MediaContentApiClient;
import com.company.app.api.client.MediaContentApiClientFactory;
import com.company.app.exception.MissingMediaContentApiClientException;

/**
 * Unit test for {@link MediaContentApiClientFactory}.
 * 
 * @author Gustavo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MediaContentApiClientFactoryTest {

	@Mock
	private ImdbMediaContentApiClient imdbMediaContentApiClient;

	@Mock
	private LastFmMediaContentApiClient lastFmMediaContentApiClient;

	@InjectMocks
	private MediaContentApiClientFactory mediaContentApiClientFactory;

	@Test
	public void testImdbApiReturnsItsClient() {
		// Given
		final String api = ImdbMediaContentApiClient.API_DESCRIPTOR;

		// When
		final MediaContentApiClient actualMediaContentApiClient = mediaContentApiClientFactory
				.getMediaContentApiClient(api);

		// Then
		Assert.assertNotNull(actualMediaContentApiClient);
		Assert.assertTrue(actualMediaContentApiClient instanceof ImdbMediaContentApiClient);
	}
	
	@Test
	public void testLastFmApiReturnsItsClient() {
		// Given
		final String api = LastFmMediaContentApiClient.API_DESCRIPTOR;

		// When
		final MediaContentApiClient actualMediaContentApiClient = mediaContentApiClientFactory
				.getMediaContentApiClient(api);

		// Then
		Assert.assertNotNull(actualMediaContentApiClient);
		Assert.assertTrue(actualMediaContentApiClient instanceof LastFmMediaContentApiClient);
	}
	
	@Test(expected = MissingMediaContentApiClientException.class)
	public void testWrongApiReturnsItsClient() {
		// Given
		final String api = "wrong";

		// When
		final MediaContentApiClient actualMediaContentApiClient = mediaContentApiClientFactory
				.getMediaContentApiClient(api);

		// Then
		Assert.assertNotNull(actualMediaContentApiClient);
		Assert.assertTrue(actualMediaContentApiClient instanceof LastFmMediaContentApiClient);
	}

}
