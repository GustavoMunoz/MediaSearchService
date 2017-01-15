package com.company.app.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.app.exception.MissingMediaContentApiClientException;

/**
 * Media Content Api Client factory.
 * 
 * @author Gustavo
 *
 */
@Component
public class MediaContentApiClientFactory {

	@Autowired
	private ImdbMediaContentApiClient imdbMediaContentApiClient;

	@Autowired
	private LastFmMediaContentApiClient lastFmMediaContentApiClient;

	/**
	 * Returns the specific {@link MediaContentApiClient} for the given api.
	 * 
	 * @param api String that represents the desired api to retrieve the media information from. 
	 * @return specific {@link MediaContentApiClient}
	 * @throws MissingMediaContentApiClientException if no api is matched
	 */
	public MediaContentApiClient getMediaContentApiClient(final String api) {
		switch (api) {
		case ImdbMediaContentApiClient.API_DESCRIPTOR: {
			return imdbMediaContentApiClient;
		}
		case LastFmMediaContentApiClient.API_DESCRIPTOR: {
			return lastFmMediaContentApiClient;
		}
		default: {
			throw new MissingMediaContentApiClientException(
					String.format("Media content api client not found for api: %s", api));
		}
		}
	}

}
