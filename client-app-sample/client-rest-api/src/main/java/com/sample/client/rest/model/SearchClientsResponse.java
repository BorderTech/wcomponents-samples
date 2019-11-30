package com.sample.client.rest.model;

import com.github.bordertech.restfriends.envelope.DataEnvelope;
import com.sample.client.model.ClientSummary;
import java.util.ArrayList;

/**
 * Search clients response.
 */
public class SearchClientsResponse implements DataEnvelope<ArrayList<ClientSummary>> {

	private ArrayList<ClientSummary> data;

	/**
	 * Default constructor.
	 */
	public SearchClientsResponse() {
	}

	/**
	 * @param data the client details
	 */
	public SearchClientsResponse(final ArrayList<ClientSummary> data) {
		this.data = data;
	}

	@Override
	public ArrayList<ClientSummary> getData() {
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}

	@Override
	public void setData(final ArrayList<ClientSummary> data) {
		this.data = data;
	}

}
