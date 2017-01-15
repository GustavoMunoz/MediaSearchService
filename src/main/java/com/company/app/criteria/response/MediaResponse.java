package com.company.app.criteria.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.company.app.exception.IllegalMediaResponseItemException;

/**
 * Holds the returned Api information in a consolidated way.
 * 
 * @author Gustavo
 *
 */
public class MediaResponse {

	private List<AbstractMediaResponseItem> mediaResponseItemList = new ArrayList<>();

	public void addMediaResponseItem(final AbstractMediaResponseItem abstractMediaResponseItem) {
		if (abstractMediaResponseItem == null) {
			throw new IllegalMediaResponseItemException("Cannot add null media response item.");
		}

		mediaResponseItemList.add(abstractMediaResponseItem);
	}
	
	public List<AbstractMediaResponseItem> getMediaResponseItemList() {
		return this.mediaResponseItemList;
	}

	public void printResponse() {
		System.out.println("---------------------------------");
		System.out.println(" Result obtained from the query: \n");
		mediaResponseItemList.stream()
				.forEach(responseItemList -> System.out.println(responseItemList.getResponseAsString()));
		System.out.println("---------------------------------");
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
