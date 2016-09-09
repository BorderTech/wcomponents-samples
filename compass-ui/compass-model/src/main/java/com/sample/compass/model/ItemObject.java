package com.sample.compass.model;

import java.util.Objects;

/**
 * Profile item.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ItemObject implements Item {

	private final ProfilePoint profilePoint;
	private final int childCount;

	public ItemObject(final ProfilePoint profilePoint, final int childCount) {
		this.profilePoint = profilePoint;
		this.childCount = childCount > 0 ? childCount : 0;
	}

	public ProfilePoint getProfilePoint() {
		return profilePoint;
	}

	public int getChildCount() {
		return childCount;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 29 * hash + Objects.hashCode(this.profilePoint);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ItemObject other = (ItemObject) obj;
		return Objects.equals(this.profilePoint, other.profilePoint);
	}

}
