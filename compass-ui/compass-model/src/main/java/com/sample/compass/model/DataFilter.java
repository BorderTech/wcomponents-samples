package com.sample.compass.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DataFilter implements Serializable {

	private Integer lastMinutes;
	private Date startTime;
	private Date endTime;
	private Integer top;
	private Map<String, String> options;

	public Integer getLastMinutes() {
		return lastMinutes;
	}

	public void setLastMinutes(final Integer lastMinutes) {
		this.lastMinutes = lastMinutes;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(final Integer top) {
		this.top = top;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(final Map<String, String> options) {
		this.options = options;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 83 * hash + Objects.hashCode(this.lastMinutes);
		hash = 83 * hash + Objects.hashCode(this.startTime);
		hash = 83 * hash + Objects.hashCode(this.endTime);
		hash = 83 * hash + Objects.hashCode(this.top);
		hash = 83 * hash + Objects.hashCode(this.options);
		return hash;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DataFilter other = (DataFilter) obj;
		if (!Objects.equals(this.lastMinutes, other.lastMinutes)) {
			return false;
		}
		if (!Objects.equals(this.startTime, other.startTime)) {
			return false;
		}
		if (!Objects.equals(this.endTime, other.endTime)) {
			return false;
		}
		if (!Objects.equals(this.top, other.top)) {
			return false;
		}
		return Objects.equals(this.options, other.options);
	}

}
