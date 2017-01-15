package com.company.app.criteria.response;

/**
 * Holds the Movie information specific attributes.
 * 
 * @author Gustavo
 *
 */
public class MovieMediaResponseItem extends AbstractMediaResponseItem {

	private String director;

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.company.app.api.criteria.AbstractMediaResponseItem.getResponseAsString()
	 */
	@Override
	public String getResponseAsString() {
		final StringBuilder stringBuilder = new StringBuilder();
		return stringBuilder.append("Title: '").append(getTitle()).append("' - Director: '").append(getDirector())
				.append("' - Year: '").append(getYear()).append("'").toString();
	}
}
