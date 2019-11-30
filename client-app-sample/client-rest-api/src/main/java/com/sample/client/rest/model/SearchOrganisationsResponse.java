package com.sample.client.rest.model;

import com.github.bordertech.restfriends.envelope.DataEnvelope;
import com.sample.client.model.OrganisationDetail;
import java.util.ArrayList;

/**
 * Search organisations response.
 */
public class SearchOrganisationsResponse implements DataEnvelope<ArrayList<OrganisationDetail>> {

	private ArrayList<OrganisationDetail> data;

	/**
	 * Default constructor.
	 */
	public SearchOrganisationsResponse() {
	}

	/**
	 * @param data the client details
	 */
	public SearchOrganisationsResponse(final ArrayList<OrganisationDetail> data) {
		this.data = data;
	}

	@Override
	public ArrayList<OrganisationDetail> getData() {
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}

	@Override
	public void setData(final ArrayList<OrganisationDetail> data) {
		this.data = data;
	}

}
