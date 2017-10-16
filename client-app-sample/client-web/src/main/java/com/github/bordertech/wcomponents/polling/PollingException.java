package com.github.bordertech.wcomponents.polling;

/**
 * Exception processing polling action.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class PollingException extends Exception {

	/**
	 * @param msg exception message
	 */
	public PollingException(final String msg) {
		super(msg);
	}

	/**
	 * @param msg exception message
	 * @param throwable original exception
	 */
	public PollingException(final String msg, final Throwable throwable) {
		super(msg, throwable);
	}

}
