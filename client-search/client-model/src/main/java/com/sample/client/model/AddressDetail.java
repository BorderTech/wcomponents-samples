package com.sample.client.model;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AddressDetail implements Serializable {

	private String street;
	private String suburb;
	private StateType state;
	private String postcode;
	private String countryCode;

	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(final String suburb) {
		this.suburb = suburb;
	}

	public StateType getState() {
		return state;
	}

	public void setState(final StateType state) {
		this.state = state;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(final String postcode) {
		this.postcode = postcode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return street + ", " + suburb + ", " + state + ", " + postcode + ", " + countryCode;
	}

}
