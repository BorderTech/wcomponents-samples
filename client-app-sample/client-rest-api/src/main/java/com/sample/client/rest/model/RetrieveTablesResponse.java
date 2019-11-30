package com.sample.client.rest.model;

import com.github.bordertech.restfriends.envelope.DataEnvelope;
import java.util.ArrayList;

/**
 * Retrieve tables response.
 */
public class RetrieveTablesResponse implements DataEnvelope<ArrayList<String>> {

	private ArrayList<String> data;

	/**
	 * Default constructor.
	 */
	public RetrieveTablesResponse() {
	}

	/**
	 * @param data the table details
	 */
	public RetrieveTablesResponse(final ArrayList<String> data) {
		this.data = data;
	}

	@Override
	public ArrayList<String> getData() {
		if (data == null) {
			data = new ArrayList<>();
		}
		return data;
	}

	@Override
	public void setData(final ArrayList<String> data) {
		this.data = data;
	}

}
