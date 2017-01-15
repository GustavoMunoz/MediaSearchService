/**
 * 
 */
package com.company.app.api.client;

import java.net.URI;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.company.app.criteria.query.QueryCriteria;
import com.company.app.criteria.response.MediaResponse;
import com.company.app.criteria.response.MusicMediaResponseItem;
import com.jayway.jsonpath.JsonPath;

/**
 * LastFm Media Content Api Client for Music.
 * 
 * @author Gustavo
 *
 */
@Component
public class LastFmMediaContentApiClient implements MediaContentApiClient {

	public static final String API_DESCRIPTOR = "lastfm";

	@Value("${lastfm.apiQueryString}")
	private String apiQueryString;

	@Value("${lastfm.apiKey}")
	private String apiKey;

	@Autowired
	private RestTemplate restTemplate;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.company.app.api.client.MediaContentApiClient#
	 * isQueryCriteriaSupportedByApi(com.company.app.criteria.query.
	 * QueryCriteria)
	 */
	@Override
	public boolean isQueryCriteriaSupportedByApi(QueryCriteria queryCriteria) {
		return queryCriteria != null && StringUtils.isEmpty(queryCriteria.getMovie())
				&& StringUtils.isNotEmpty(queryCriteria.getMusic());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.company.app.api.client.MediaContentApiClient#getMediaContent(com.
	 * ksubaka.app.criteria.query.QueryCriteria)
	 */
	@Override
	public MediaResponse getMediaContent(QueryCriteria queryCriteria) {
		final URI targetUrl = UriComponentsBuilder.fromUriString(apiQueryString)
				.queryParam("album", queryCriteria.getMusic()).queryParam("api_key", apiKey).build().toUri();
		
		final String rawResponse = restTemplate.getForObject(targetUrl, String.class);
		final MediaResponse mediaResponse = buildMediaResponseFromRawReponse(rawResponse);
		return mediaResponse;
	}

	private MediaResponse buildMediaResponseFromRawReponse(String rawResponse) {
		final MediaResponse mediaResponse = new MediaResponse();

		final List<String> albums = JsonPath.read(rawResponse, "$.results.albummatches.album[*].name");
		final List<String> artists = JsonPath.read(rawResponse, "$.results.albummatches.album[*].artist");

		for (int index = 0; index < albums.size(); index++) {

			final MusicMediaResponseItem musicMediaResponseItem = new MusicMediaResponseItem();
			musicMediaResponseItem.setTitle(albums.get(index));
			musicMediaResponseItem.setArtist(artists.get(index));
			musicMediaResponseItem.setYear("Unknown");
			mediaResponse.addMediaResponseItem(musicMediaResponseItem);
		}
		return mediaResponse;
	}

}
