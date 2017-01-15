package com.company.app.criteria.response;

/**
 * Holds the Music information related to a given API request.
 * 
 * @author Gustavo
 *
 */
public class MusicMediaResponseItem extends AbstractMediaResponseItem {

	private String artist;
	
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
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
		return stringBuilder.append("Title: '").append(getTitle()).append("' - Artist: '").append(getArtist())
				.append("' - Year: '").append(getYear()).append("'").toString();
	}
	
}
