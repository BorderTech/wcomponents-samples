package com.sample.client.rest.model;

import com.github.bordertech.restfriends.envelope.DataEnvelope;
import com.sample.client.model.IndividualDetail;
import java.util.ArrayList;

/**
 * Search individuals response.
 */
public class SearchIndividualsResponse implements DataEnvelope<ArrayList<IndividualDetail>> {

	private ArrayList<IndividualDetail> data;

	/**
	 * Default constructor.
	 */
	public SearchIndividualsResponse() {
	}

	/**
	 * @param data the client details
	 */
	public SearchIndividualsResponse(final ArrayList<IndividualDetail> data) {
		this.data = data;
	}

	@Override
	public ArrayList<IndividualDetail> getData() {
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}

	@Override
	public void setData(final ArrayList<IndividualDetail> data) {
		this.data = data;
	}

}
