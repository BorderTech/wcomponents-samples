package com.sample.client.rest.model;

import com.github.bordertech.restfriends.envelope.DataEnvelope;
import com.sample.client.model.IndividualDetail;

/**
 * Retrieve individual response.
 */
public class IndividualDetailResponse implements DataEnvelope<IndividualDetail> {

	private IndividualDetail data;

	/**
	 * Default constructor.
	 */
	public IndividualDetailResponse() {
	}

	/**
	 * @param data the individual details
	 */
	public IndividualDetailResponse(final IndividualDetail data) {
		this.data = data;
	}

	@Override
	public IndividualDetail getData() {
		return data;
	}

	@Override
	public void setData(final IndividualDetail data) {
		this.data = data;
	}

}
