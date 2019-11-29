package com.sample.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Individual details.
 */
public class IndividualDetail implements Serializable {

	private String clientId;
	private String firstName;
	private String lastName;
	private String email;
	private Date dateOfBirth;
	private AddressDetail address;
	private List<PassportDetail> identifications;

	/**
	 * @return the client id
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the client id
	 */
	public void setClientId(final String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the first name
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the last name
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the client email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the client email
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @return the date of birth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the date of birth
	 */
	public void setDateOfBirth(final Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the address details
	 */
	public AddressDetail getAddress() {
		return address;
	}

	/**
	 * @param address the address details
	 */
	public void setAddress(final AddressDetail address) {
		this.address = address;
	}

	/**
	 * @return the client identifications
	 */
	public List<PassportDetail> getIdentifications() {
		if (identifications == null) {
			identifications = new ArrayList<>();
		}
		return identifications;
	}

	/**
	 * @param identifications the client identifications
	 */
	public void setIdentifications(final List<PassportDetail> identifications) {
		this.identifications = identifications;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 97 * hash + Objects.hashCode(this.clientId);
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
		final IndividualDetail other = (IndividualDetail) obj;
		if (!Objects.equals(this.clientId, other.clientId)) {
			return false;
		}
		return true;
	}

}
