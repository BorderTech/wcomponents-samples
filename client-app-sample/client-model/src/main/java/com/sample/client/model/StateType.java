package com.sample.client.model;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum StateType {
	/**
	 * ACT State.
	 */
	ACT,
	/**
	 * NSW State.
	 */
	NSW,
	/**
	 * NT State.
	 */
	NT,
	/**
	 * QLD State.
	 */
	QLD,
	/**
	 * TAS State.
	 */
	TAS,
	/**
	 * VIC State.
	 */
	VIC,
	/**
	 * WA State.
	 */
	WA;

	@Override
	public String toString() {
		return name();
	}
}
