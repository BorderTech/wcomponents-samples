package com.sample.compass.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Profile point.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ProfilePoint implements Serializable {

	private final String domainId;
	private final String environmentId;
	private final String id;
	private final String name;

	public ProfilePoint(final String domainId, final String name) {
		this.domainId = domainId;
		this.environmentId = null;
		this.id = null;
		this.name = name;
	}

	public ProfilePoint(final String domainId, final String environmentId, final String name) {
		this.domainId = domainId;
		this.environmentId = environmentId;
		this.id = null;
		this.name = name;
	}

	public ProfilePoint(final String domainId, final String environmentId, final String id, final String name) {
		this.domainId = domainId;
		this.environmentId = environmentId;
		this.id = id;
		this.name = name;
	}

	public String getDomainId() {
		return domainId;
	}

	public String getEnvironmentId() {
		return environmentId;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + Objects.hashCode(this.domainId);
		hash = 59 * hash + Objects.hashCode(this.environmentId);
		hash = 59 * hash + Objects.hashCode(this.id);
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
		final ProfilePoint other = (ProfilePoint) obj;
		if (!Objects.equals(this.domainId, other.domainId)) {
			return false;
		}
		if (!Objects.equals(this.environmentId, other.environmentId)) {
			return false;
		}
		return Objects.equals(this.id, other.id);
	}

}
