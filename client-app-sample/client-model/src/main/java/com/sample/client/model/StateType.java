package com.sample.client.model;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum StateType {
	ACT,
	NSW,
	NT,
	QLD,
	TAS,
	VIC,
	WA;

	@Override
	public String toString() {
		return name();
	}
}
