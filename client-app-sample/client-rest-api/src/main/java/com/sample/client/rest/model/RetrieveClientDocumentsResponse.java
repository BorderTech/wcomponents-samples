package com.sample.client.rest.model;

import com.github.bordertech.restfriends.envelope.DataEnvelope;
import com.sample.client.model.DocumentDetail;
import java.util.ArrayList;

/**
 * Retrieve documents response.
 */
public class RetrieveClientDocumentsResponse implements DataEnvelope<ArrayList<DocumentDetail>> {

	private ArrayList<DocumentDetail> data;

	/**
	 * Default constructor.
	 */
	public RetrieveClientDocumentsResponse() {
	}

	/**
	 * @param data the code options
	 */
	public RetrieveClientDocumentsResponse(final ArrayList<DocumentDetail> data) {
		this.data = data;
	}

	@Override
	public ArrayList<DocumentDetail> getData() {
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}

	@Override
	public void setData(final ArrayList<DocumentDetail> data) {
		this.data = data;
	}

}
