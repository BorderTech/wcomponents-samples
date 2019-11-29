package com.sample.client.model;

import java.io.Serializable;

/**
 * Address details.
 */
public class AddressDetail implements Serializable {

	private String street;
	private String suburb;
	private StateType state;
	private String postcode;
	private String countryCode;

	/**
	 * @return the address street details
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the address street details
	 */
	public void setStreet(final String street) {
		this.street = street;
	}

	/**
	 * @return the address suburb name
	 */
	public String getSuburb() {
		return suburb;
	}

	/**
	 * @param suburb the address suburb name
	 */
	public void setSuburb(final String suburb) {
		this.suburb = suburb;
	}

	/**
	 * @return the address state
	 */
	public StateType getState() {
		return state;
	}

	/**
	 * @param state the address state
	 */
	public void setState(final StateType state) {
		this.state = state;
	}

	/**
	 * @return the address postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode the address postcode
	 */
	public void setPostcode(final String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return the address country code
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the address country code
	 */
	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return street + ", " + suburb + ", " + state + ", " + postcode + ", " + countryCode;
	}

}
