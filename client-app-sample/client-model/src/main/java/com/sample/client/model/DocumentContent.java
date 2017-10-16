package com.sample.client.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Document content.
 *
 * @author jonathan
 */
public class DocumentContent implements Serializable {

	private final String documentId;

	private final byte[] bytes;

	private final String filename;

	private final String mimeType;

	public DocumentContent(final String documentId, final byte[] bytes, final String filename, final String mimeType) {
		this.documentId = documentId;
		this.bytes = bytes;
		this.filename = filename;
		this.mimeType = mimeType;
	}

	public String getDocumentId() {
		return documentId;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public String getFilename() {
		return filename;
	}

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
	public boolean equals(Object obj) {
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
