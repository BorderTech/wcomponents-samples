package com.github.bordertech.wcomponents.polling;

/**
 * Status of the Polling Panel and if the polling action has been processed successfully.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum PollingStatus {
	/**
	 * Polling not started yet.
	 */
	NOT_STARTED,
	/**
	 * Polling started.
	 */
	STARTED,
	/**
	 * Error occurred.
	 */
	ERROR,
	/**
	 * Polling action completed successfully.
	 */
	COMPLETE

}
