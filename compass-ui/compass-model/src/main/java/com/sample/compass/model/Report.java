package com.sample.compass.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Report implements Serializable {

	private final String reportId;
	private String title;
	private List<Chart> charts;

	public Report(final String reportId) {
		this.reportId = reportId;
	}

	public String getReportId() {
		return reportId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public List<Chart> getCharts() {
		if (charts == null) {
			charts = new ArrayList<>();
		}
		return charts;
	}

	public void setChart(final List<Chart> charts) {
		this.charts = charts;
	}

}
