package com.sample.compass.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ItemMeta implements Serializable {

	private final DataModel attributes;
	private final List<String> reportIds;

	public ItemMeta(final DataModel attributes, final List<String> reportIds) {
		this.attributes = attributes;
		this.reportIds = reportIds;
	}

	public DataModel getAttributes() {
		return attributes;
	}

	public List<String> getReportIds() {
		return reportIds;
	}

}
