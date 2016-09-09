package com.sample.compass.model;

import java.io.Serializable;

/**
 * The details of an attribute. Used to describe the attributes of a data model.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DataAttributeMeta implements Serializable {

	/**
	 * Attribute type.
	 */
	public enum AttributeType {
		DATE, TIME, STRING, INTEGER, DECIMAL;
	}

	private final AttributeType type;
	private final String desc;

	public DataAttributeMeta(final String desc) {
		this(AttributeType.STRING, desc);
	}

	public DataAttributeMeta(final AttributeType type, final String desc) {
		if (type == null) {
			throw new IllegalArgumentException("Type must be provided.");
		}
		if (desc == null || desc.isEmpty()) {
			throw new IllegalArgumentException("Description must be provided.");
		}
		this.type = type;
		this.desc = desc;
	}

	public AttributeType getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

}
