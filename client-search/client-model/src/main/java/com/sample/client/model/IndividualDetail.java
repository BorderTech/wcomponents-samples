package com.sample.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class IndividualDetail implements Serializable {

	private String clientId;
	private String firstName;
	private String lastName;
	private String email;
	private Date dateOfBirth;
	private AddressDetail address;
	private List<PassportDetail> identifications;

	public IndividualDetail() {
		identifications = new ArrayList<>();
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(final Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public AddressDetail getAddress() {
		return address;
	}

	public void setAddress(final AddressDetail address) {
		this.address = address;
	}

	public List<PassportDetail> getIdentifications() {
		return identifications;
	}

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
	public boolean equals(Object obj) {
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
