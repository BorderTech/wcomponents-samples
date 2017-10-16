package com.sample.client.services.exception;

/**
 * Exception processing services.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ServiceException extends Exception {

	/**
	 * @param msg exception message
	 */
	public ServiceException(final String msg) {
		this(msg, null);
	}

	/**
	 * @param msg exception message
	 * @param throwable original exception
	 */
	public ServiceException(final String msg, final Throwable throwable) {
		super(msg, throwable);
	}

}
