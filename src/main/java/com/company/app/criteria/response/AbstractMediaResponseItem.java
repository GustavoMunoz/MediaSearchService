package com.company.app.criteria.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Abstract class for all the media response common attributes. 
 * 
 * @author Gustavo
 *
 */
public abstract class AbstractMediaResponseItem {
	
	private String title;
	
	private String year;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * Returns the representation of the media response item as string.
	 * 
	 * @return String with the specific representation
	 */
	public abstract String getResponseAsString();
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
