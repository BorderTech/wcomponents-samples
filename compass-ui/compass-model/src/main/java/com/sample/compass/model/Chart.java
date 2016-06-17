package com.sample.compass.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Chart implements Serializable {

	private final String chartId;
	private String title;
	private List<DataSource> dataSources;
	private ChartType chartType;

	public Chart(final String chartId) {
		this.chartId = chartId;
	}

	public String getChartId() {
		return chartId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public List<DataSource> getDataSources() {
		if (dataSources == null) {
			dataSources = new ArrayList<>();
		}
		return dataSources;
	}

	public void setDataSources(final List<DataSource> dataSources) {
		this.dataSources = dataSources;
	}

	public ChartType getChartType() {
		return chartType;
	}

	public void setChartType(final ChartType chartType) {
		this.chartType = chartType;
	}

}
