package com.sample.client.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Document content details.
 */
public class DocumentContent implements Serializable {

	private final String documentId;
	private final byte[] bytes;
	private final String filename;
	private final String mimeType;

	/**
	 * @param documentId the document id
	 * @param bytes the document content
	 * @param filename the document file name
	 * @param mimeType the content mime type
	 */
	public DocumentContent(final String documentId, final byte[] bytes, final String filename, final String mimeType) {
		this.documentId = documentId;
		this.bytes = bytes;
		this.filename = filename;
		this.mimeType = mimeType;
	}

	/**
	 * @return the document id
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * @return the document content
	 */
	public byte[] getBytes() {
		return bytes;
	}

	/**
	 * @return the document file name
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @return the content mime type
	 */
	public String getMimeType() {
		return mimeType;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 59 * hash + Objects.hashCode(this.documentId);
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
		final DocumentContent other = (DocumentContent) obj;
		if (!Objects.equals(this.documentId, other.documentId)) {
			return false;
		}
		return true;
	}

}
