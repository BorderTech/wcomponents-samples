package com.sample.client.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class PassportDetail implements Serializable {

	private String countryCode;
	private String passportNumber;

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

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
