package com.sample.client.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class OrganisationDetail implements Serializable {

	private String clientId;
	private String name;
	private String abn;
	private String email;
	private AddressDetail address;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAbn() {
		return abn;
	}

	public void setAbn(final String abn) {
		this.abn = abn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public AddressDetail getAddress() {
		return address;
	}

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
		final OrganisationDetail other = (OrganisationDetail) obj;
		if (!Objects.equals(this.clientId, other.clientId)) {
			return false;
		}
		return true;
	}

}
