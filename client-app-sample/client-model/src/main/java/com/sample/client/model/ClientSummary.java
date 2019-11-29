package com.sample.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Client summary.
 */
public class ClientSummary implements Serializable {

	private final String clientId;
	private final ClientType type;
	private String name;
	private String address;
	private List<PassportDetail> identifications;

	/**
	 * @param clientId the client ID
	 * @param type the client type
	 */
	public ClientSummary(final String clientId, final ClientType type) {
		this.clientId = clientId;
		this.type = type;
		this.identifications = new ArrayList<>();
	}

	/**
	 * @return the client id
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @return the client type
	 */
	public ClientType getType() {
		return type;
	}

	/**
	 * @return the client name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the client name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the client address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the client address
	 */
	public void setAddress(final String address) {
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
		int hash = 7;
		hash = 61 * hash + Objects.hashCode(this.clientId);
		hash = 61 * hash + Objects.hashCode(this.type);
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
		final ClientSummary other = (ClientSummary) obj;
		if (!Objects.equals(this.clientId, other.clientId)) {
			return false;
		}
		if (this.type != other.type) {
			return false;
		}
		return true;
	}

}
