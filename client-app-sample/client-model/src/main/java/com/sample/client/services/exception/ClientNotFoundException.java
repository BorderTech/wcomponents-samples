package com.sample.client.services.exception;

/**
 * Client not found.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ClientNotFoundException extends ServiceException {

	/**
	 * Construct exception.
	 */
	public ClientNotFoundException() {
		super("Client not found");
	}

}
