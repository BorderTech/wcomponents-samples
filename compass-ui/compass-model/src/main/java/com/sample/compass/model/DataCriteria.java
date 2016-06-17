package com.sample.compass.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DataCriteria implements Serializable {

	private DataSource dataSource;
	private Integer lastMinutes;
	private Date startTime;
	private Date endTime;
	private Integer top;
	private Map<String, String> options;

}
