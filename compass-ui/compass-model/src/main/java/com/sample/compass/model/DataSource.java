package com.sample.compass.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DataSource implements Serializable {

	private String wildCard;
	private ProfilePoint profilePoint;
	private DataFilter filter;

	public String getWildCard() {
		return wildCard;
	}

	public void setWildCard(final String wildCard) {
		this.wildCard = wildCard;
	}

	public ProfilePoint getProfilePoint() {
		return profilePoint;
	}

	public void setProfilePoint(final ProfilePoint profilePoint) {
		this.profilePoint = profilePoint;
	}

	public DataFilter getFilter() {
		return filter;
	}

	public void setFilter(final DataFilter filter) {
		this.filter = filter;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + Objects.hashCode(this.wildCard);
		hash = 89 * hash + Objects.hashCode(this.profilePoint);
		hash = 89 * hash + Objects.hashCode(this.filter);
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
		final DataSource other = (DataSource) obj;
		if (!Objects.equals(this.wildCard, other.wildCard)) {
			return false;
		}
		if (!Objects.equals(this.profilePoint, other.profilePoint)) {
			return false;
		}
		return Objects.equals(this.filter, other.filter);
	}

}
