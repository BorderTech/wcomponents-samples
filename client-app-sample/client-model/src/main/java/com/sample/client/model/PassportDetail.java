package com.sample.client.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Passport detail.
 */
public class PassportDetail implements Serializable {

	private String countryCode;
	private String passportNumber;

	/**
	 * @return the country code
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the country code
	 */
	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the passport number
	 */
	public String getPassportNumber() {
		return passportNumber;
	}

	/**
	 * @param passportNumber the passport number
	 */
	public void setPassportNumber(final String passportNumber) {
		this.passportNumber = passportNumber;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 41 * hash + Objects.hashCode(this.countryCode);
		hash = 41 * hash + Objects.hashCode(this.passportNumber);
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PassportDetail other = (PassportDetail) obj;
		if (!Objects.equals(this.countryCode, other.countryCode)) {
			return false;
		}
		if (!Objects.equals(this.passportNumber, other.passportNumber)) {
			return false;
		}
		return true;
	}

}
