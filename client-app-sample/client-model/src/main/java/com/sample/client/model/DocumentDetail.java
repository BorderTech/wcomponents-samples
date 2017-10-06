package com.sample.client.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Document Details.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DocumentDetail implements Serializable {

	private String documentId;
	private String description;
	private String resourcePath;

	public DocumentDetail() {
	}

	public DocumentDetail(final String documentId, final String description, final String resourcePath) {
		this.documentId = documentId;
		this.description = description;
		this.resourcePath = resourcePath;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(final String documentId) {
		this.documentId = documentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(final String resourcePath) {
		this.resourcePath = resourcePath;
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
		final DocumentDetail other = (DocumentDetail) obj;
		if (!Objects.equals(this.documentId, other.documentId)) {
			return false;
		}
		return true;
	}

}
