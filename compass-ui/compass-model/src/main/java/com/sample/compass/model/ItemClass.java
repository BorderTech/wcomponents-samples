package com.sample.compass.model;

import java.util.Objects;

/**
 * Profile item.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ItemClass implements Item {

	private final ProfilePoint profilePoint;
	private final String className;
	private final int childCount;

	public ItemClass(final ProfilePoint profilePoint, final String className, final int childCount) {
		this.profilePoint = profilePoint;
		this.className = className;
		this.childCount = childCount > 0 ? childCount : 0;
	}

	public ProfilePoint getProfilePoint() {
		return profilePoint;
	}

	public String getClassName() {
		return className;
	}

	public int getChildCount() {
		return childCount;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 29 * hash + Objects.hashCode(this.profilePoint);
		hash = 29 * hash + Objects.hashCode(this.className);
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
		final ItemClass other = (ItemClass) obj;
		if (!Objects.equals(this.className, other.className)) {
			return false;
		}
		return Objects.equals(this.profilePoint, other.profilePoint);
	}

}
