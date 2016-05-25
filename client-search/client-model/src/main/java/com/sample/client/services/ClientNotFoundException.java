package com.sample.client.services;

/**
 * Client not found.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ClientNotFoundException extends Exception {

	/**
	 * Construct exception.
	 */
	public ClientNotFoundException() {
		super("Client not found");
	}

}
