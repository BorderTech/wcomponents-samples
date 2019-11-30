package com.sample.client.rest.model;

import com.github.bordertech.restfriends.envelope.DataEnvelope;
import com.sample.client.model.DocumentContent;

/**
 * Retrieve document content response.
 */
public class DocumentContentResponse implements DataEnvelope<DocumentContent> {

	private DocumentContent data;

	/**
	 * Default constructor.
	 */
	public DocumentContentResponse() {
	}

	/**
	 * @param data the document content
	 */
	public DocumentContentResponse(final DocumentContent data) {
		this.data = data;
	}

	@Override
	public DocumentContent getData() {
		return data;
	}

	@Override
	public void setData(final DocumentContent data) {
		this.data = data;
	}

}
