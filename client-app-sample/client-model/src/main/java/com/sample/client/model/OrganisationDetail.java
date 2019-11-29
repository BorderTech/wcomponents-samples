package com.sample.client.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Organisation details.
 */
public class OrganisationDetail implements Serializable {

	private String clientId;
	private String name;
	private String abn;
	private String email;
	private AddressDetail address;

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
	 * @return the organisation name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the organisation name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the organisation ABN
	 */
	public String getAbn() {
		return abn;
	}

	/**
	 * @param abn the organisation ABN
	 */
	public void setAbn(final String abn) {
		this.abn = abn;
	}

	/**
	 * @return the organisation email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the organisation email
	 */
	public void setEmail(final String email) {
		this.email = email;
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

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 59 * hash + Objects.hashCode(this.clientId);
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
		final OrganisationDetail other = (OrganisationDetail) obj;
		if (!Objects.equals(this.clientId, other.clientId)) {
			return false;
		}
		return true;
	}

}
