package com.company.app.criteria.query;


import org.apache.commons.lang3.StringUtils;

import com.company.app.exception.InvalidQueryCriteriaException;

/**
 * Holds the criteria information to be used when searching. It validates the mandatory arguments in object construction time.
 * 
 * @author Gustavo
 *
 */
public class QueryCriteria {

	private final String api;
	
	private final String movie;
	
	private final String music;
	
	public QueryCriteria(final QueryCriteriaBuilder queryCriteriaBuilder) {
		this.api = queryCriteriaBuilder.api;
		this.movie = queryCriteriaBuilder.movie;
		this.music = queryCriteriaBuilder.music;
	}

	public String getApi() {
		return api;
	}

	public String getMovie() {
		return movie;
	}

	public String getMusic() {
		return music;
	}
	
	public static class QueryCriteriaBuilder {
		
		private String api;
		
		private String movie;
		
		private String music;
		
		public QueryCriteriaBuilder withApi(final String api) {
			this.api = api;
			return this;
		}
		
		public QueryCriteriaBuilder withMovie(final String movie) {
			this.movie = movie;
			return this;
		}
		
		public QueryCriteriaBuilder withMusic(final String music) {
			this.music = music;
			return this;
		}
		
		public QueryCriteria build() {
			if (StringUtils.isEmpty(api)) {
				throw new InvalidQueryCriteriaException("Api cannot be null.");
			}

			if (StringUtils.isEmpty(movie) && StringUtils.isEmpty(music)) {
				throw new InvalidQueryCriteriaException(
						"Either 'music' or 'movie' cannot be null");
			}
			
			if (StringUtils.isNotEmpty(movie) && StringUtils.isNotEmpty(music)) {
				throw new InvalidQueryCriteriaException(
						"Both 'music' and 'movie' cannot be specified");
			}
			
			return new QueryCriteria(this);
		}
	}
}
