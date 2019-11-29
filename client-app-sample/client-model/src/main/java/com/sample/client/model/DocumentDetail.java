package com.sample.client.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Document details.
 */
public class DocumentDetail implements Serializable {

	private String documentId;
	private String description;
	private Date submitDate;
	private String resourcePath;

	/**
	 * Default constructor.
	 */
	public DocumentDetail() {
	}

	/**
	 * @param documentId the document id
	 * @param description the document description
	 * @param submitDate the date the document was submitted
	 * @param resourcePath the path to the document
	 */
	public DocumentDetail(final String documentId, final String description, final Date submitDate, final String resourcePath) {
		this.documentId = documentId;
		this.description = description;
		this.submitDate = submitDate;
		this.resourcePath = resourcePath;
	}

	/**
	 * @return the document id
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * @param documentId the document id
	 */
	public void setDocumentId(final String documentId) {
		this.documentId = documentId;
	}

	/**
	 * @return the document description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the document description
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the date the document was submitted
	 */
	public Date getSubmitDate() {
		return submitDate;
	}

	/**
	 * @param submitDate the date the document was submitted
	 */
	public void setSubmitDate(final Date submitDate) {
		this.submitDate = submitDate;
	}

	/**
	 * @return the path to the file
	 */
	public String getResourcePath() {
		return resourcePath;
	}

	/**
	 * @param resourcePath the path to the file
	 */
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
		final DocumentDetail other = (DocumentDetail) obj;
		if (!Objects.equals(this.documentId, other.documentId)) {
			return false;
		}
		return true;
	}

}
