package com.sample.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ClientSummary implements Serializable {

	private final String clientId;
	private final ClientType type;
	private String name;
	private String address;
	private List<PassportDetail> identifications;

	public ClientSummary(final String clientId, final ClientType type) {
		this.clientId = clientId;
		this.type = type;
		this.identifications = new ArrayList<>();
	}

	public String getClientId() {
		return clientId;
	}

	public ClientType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
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
		int hash = 7;
		hash = 61 * hash + Objects.hashCode(this.clientId);
		hash = 61 * hash + Objects.hashCode(this.type);
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
