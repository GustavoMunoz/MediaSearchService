package com.company.app.api.client;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.company.app.criteria.query.QueryCriteria;
import com.company.app.criteria.response.MediaResponse;
import com.company.app.criteria.response.MovieMediaResponseItem;
import com.jayway.jsonpath.JsonPath;

/**
 * IMDB Media Content Api Client for Movies.
 * 
 * @author Gustavo
 *
 */
@Component
public class ImdbMediaContentApiClient implements MediaContentApiClient {

	public static final String API_DESCRIPTOR = "imdb";

	@Value("${imdb.apiQueryString}")
	private String apiQueryString;

	@Autowired
	private RestTemplate restTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.company.app.api.client.MediaContentApiClient#
	 * isQueryCriteriaSupportedByApi(com.company.app.criteria.QueryCriteria)
	 */
	@Override
	public boolean isQueryCriteriaSupportedByApi(final QueryCriteria queryCriteria) {
		return queryCriteria != null && StringUtils.isNotEmpty(queryCriteria.getMovie())
				&& StringUtils.isEmpty(queryCriteria.getMusic());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.company.app.api.client.MediaContentApiClient#getMediaContent(com.
	 * ksubaka. criteria.QueryCriteria)
	 */
	@Override
	public MediaResponse getMediaContent(final QueryCriteria queryCriteria) {
		final URI targetUrl = UriComponentsBuilder.fromUriString(apiQueryString)
				.queryParam("q", queryCriteria.getMovie()).build().toUri();

		final String rawResponse = restTemplate.getForObject(targetUrl, String.class);
		final MediaResponse mediaResponse = buildMediaResponseFromRawReponse(rawResponse);
		return mediaResponse;
	}

	private MediaResponse buildMediaResponseFromRawReponse(final String rawResponse) {
		final MediaResponse mediaResponse = new MediaResponse();

		if (hasMovies(rawResponse)) {
			final List<String> titles = JsonPath.read(rawResponse, "$.title_popular[*].title");
			final List<String> descriptions = JsonPath.read(rawResponse, "$.title_popular[*].description");

			for (int index = 0; index < titles.size(); index++) {

				final String description = descriptions.get(index);
				if (isMovie(description)) {

					final MovieMediaResponseItem movieMediaResponseItem = new MovieMediaResponseItem();
					movieMediaResponseItem.setTitle(titles.get(index));

					movieMediaResponseItem.setYear(description.substring(0, description.indexOf(",")));
					movieMediaResponseItem.setDirector(
							description.substring(description.indexOf(">") + 1, description.lastIndexOf("<")));
					mediaResponse.addMediaResponseItem(movieMediaResponseItem);
				}
			}
		} 
		return mediaResponse;
	}

	@SuppressWarnings("rawtypes")
	private boolean hasMovies(String rawResponse) {
		final Map map = JsonPath.read(rawResponse, "$");
		return map.size() > 0;
	}

	private boolean isMovie(String description) {
		return !description.contains("TV series") && !description.contains("documentary");
	}

}
