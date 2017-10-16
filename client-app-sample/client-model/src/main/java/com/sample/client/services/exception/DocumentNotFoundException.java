package com.sample.client.services.exception;

/**
 * Document not found.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DocumentNotFoundException extends ServiceException {

	/**
	 * Construct exception.
	 */
	public DocumentNotFoundException() {
		super("Document not found");
	}

}
