package com.company.app.criteria.query;

import org.springframework.stereotype.Component;

import com.company.app.criteria.query.QueryCriteria.QueryCriteriaBuilder;

/**
 * Builds a search criteria based on environment configuration.
 * 
 * @author Gustavo
 *
 */
@Component
public class EnvironmentQueryCriteriaGenerator implements QueryCriteriaGenerator {

	/* (non-Javadoc)
	 * @see com.company.app.criteria.QueryCriteriaGenerator#generate()
	 */
	@Override
	public QueryCriteria generate() {
		
		final String api = System.getProperty("api");
		final String movie = System.getProperty("movie");
		final String music = System.getProperty("music");
		
		return new QueryCriteriaBuilder().withApi(api).withMovie(movie).withMusic(music).build();
	}
	
}
