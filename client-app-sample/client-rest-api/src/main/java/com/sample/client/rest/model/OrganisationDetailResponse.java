package com.sample.client.rest.model;

import com.github.bordertech.restfriends.envelope.DataEnvelope;
import com.sample.client.model.OrganisationDetail;

/**
 * Retrieve origanisation response.
 */
public class OrganisationDetailResponse implements DataEnvelope<OrganisationDetail> {

	private OrganisationDetail data;

	/**
	 * Default constructor.
	 */
	public OrganisationDetailResponse() {
	}

	/**
	 * @param data the organisation details
	 */
	public OrganisationDetailResponse(final OrganisationDetail data) {
		this.data = data;
	}

	@Override
	public OrganisationDetail getData() {
		return data;
	}

	@Override
	public void setData(final OrganisationDetail data) {
		this.data = data;
	}

}
