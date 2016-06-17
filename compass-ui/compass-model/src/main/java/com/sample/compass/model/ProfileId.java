package com.sample.compass.model;

import java.io.Serializable;

/**
 * Profile point id.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ProfileId implements Serializable {

	private final String domainId;
	private final String environmentId;
	private final String id;

	public ProfileId(final String domainId) {
		this(domainId, null, null);
	}

	public ProfileId(final String domainId, final String environmentId) {
		this(domainId, environmentId, null);
	}

	public ProfileId(final String domainId, final String environmentId, final String id) {
		this.domainId = domainId;
		this.environmentId = environmentId;
		this.id = id;
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

}
