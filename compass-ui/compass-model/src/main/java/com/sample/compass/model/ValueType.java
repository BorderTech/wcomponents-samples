package com.sample.compass.model;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum ValueType {
	DATE, TIME, STRING, INTEGER, DECIMAL;

	@Override
	public String toString() {
		return name();
	}

}
