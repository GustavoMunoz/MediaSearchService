package com.company.app.criteria.query;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.company.app.criteria.query.QueryCriteria;
import com.company.app.criteria.query.QueryCriteria.QueryCriteriaBuilder;
import com.company.app.exception.InvalidQueryCriteriaException;


/**
 * Unit test for {@link QueryCriteria}.
 * 
 * @author Gustavo
 *
 */
@RunWith(Parameterized.class)
public class QueryCriteriaTest {

	enum Type {
		VALID_DATA, WRONG_DATA
	};

	private static String API = "api";

	private static String MOVIE = "movie";

	private static String MUSIC = "music";

	private Type dataType;
	private String api;
	private String movie;
	private String music;

	public QueryCriteriaTest(final Type dataType, final String api, final String movie, final String music) {
		this.dataType = dataType;
		this.api = api;
		this.movie = movie;
		this.music = music;
	}

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays
				.asList(new Object[][] { 
					{ Type.WRONG_DATA, null, null, null }, 
					{ Type.WRONG_DATA, null, null, MUSIC },
					{ Type.WRONG_DATA, null, MOVIE, null }, 
					{ Type.WRONG_DATA, null, MOVIE, MUSIC },
					{ Type.WRONG_DATA, API, MOVIE, MUSIC }, 
					{ Type.WRONG_DATA, API, null, null },

					{ Type.WRONG_DATA, "", "", "" }, 
					{ Type.WRONG_DATA, "", "", MUSIC },
					{ Type.WRONG_DATA, "", MOVIE, "" }, 
					{ Type.WRONG_DATA, "", MOVIE, MUSIC },
					{ Type.WRONG_DATA, API, MOVIE, MUSIC }, 
					{ Type.WRONG_DATA, API, "", "" }, 
					
					{ Type.VALID_DATA, API, MOVIE, null }, 
					{ Type.VALID_DATA, API, MOVIE, "" },
					{ Type.VALID_DATA, API, null, MUSIC }, 
					{ Type.VALID_DATA, API, "", MUSIC },
				}
			);
	}

	@Test(expected = InvalidQueryCriteriaException.class)
	public void invalidData() {
		// Given
		Assume.assumeTrue(dataType == Type.WRONG_DATA);
		final QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder();
		queryCriteriaBuilder.withApi(api).withMovie(movie).withMusic(music);
		
		// When
		queryCriteriaBuilder.build();
	}
	
	@Test
	public void validData() {
		// Given
		Assume.assumeTrue(dataType == Type.VALID_DATA);
		final QueryCriteriaBuilder queryCriteriaBuilder = new QueryCriteriaBuilder();
		queryCriteriaBuilder.withApi(api).withMovie(movie).withMusic(music);
		
		// When
		final QueryCriteria queryCriteria = queryCriteriaBuilder.build();
		
		// Then
		Assert.assertNotNull(queryCriteria);
		Assert.assertEquals(this.api, queryCriteria.getApi());
		Assert.assertEquals(this.movie, queryCriteria.getMovie());
		Assert.assertEquals(this.music, queryCriteria.getMusic());
	}
}
