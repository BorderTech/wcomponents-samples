package com.sample.compass.model;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AttributeMeta implements Serializable {

	private final AttributeType type;
	private final String desc;

	public AttributeMeta(final String desc) {
		this(AttributeType.STRING, desc);
	}

	public AttributeMeta(final AttributeType type, final String desc) {
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
