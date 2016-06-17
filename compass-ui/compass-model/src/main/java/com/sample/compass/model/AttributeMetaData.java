package com.sample.compass.model;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AttributeMetaData implements Serializable {

	private final String desc;
	private final ValueType type;

	public AttributeMetaData(final String desc) {
		this(desc, ValueType.STRING);
	}

	public AttributeMetaData(final String desc, final ValueType type) {
		if (desc == null || desc.isEmpty()) {
			throw new IllegalArgumentException("Description must be provided.");
		}
		if (type == null) {
			throw new IllegalArgumentException("Type must be provided.");
		}
		this.type = type;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public ValueType getType() {
		return type;
	}

}
