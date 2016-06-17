package com.sample.compass.model;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum DataType {
	OBJECT, CLASS;

	@Override
	public String toString() {
		return name();
	}

}
