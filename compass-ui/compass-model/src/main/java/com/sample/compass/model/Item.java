package com.sample.compass.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Profile item.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class Item implements Serializable {

	private final ItemType type;
	private final ProfilePoint profilePoint;
	private final String className;
	private final int childCount;

	public Item(final ProfilePoint profilePoint, final int childCount) {
		this.type = ItemType.OBJECT;
		this.profilePoint = profilePoint;
		this.className = null;
		this.childCount = childCount > 0 ? childCount : 0;
	}

	public Item(final ProfilePoint profilePoint, final String className, final int childCount) {
		this.type = ItemType.CLASS;
		this.profilePoint = profilePoint;
		this.className = className;
		this.childCount = childCount > 0 ? childCount : 0;
	}

	public ItemType getType() {
		return type;
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
		hash = 29 * hash + Objects.hashCode(this.type);
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
		final Item other = (Item) obj;
		if (!Objects.equals(this.className, other.className)) {
			return false;
		}
		if (this.type != other.type) {
			return false;
		}
		return Objects.equals(this.profilePoint, other.profilePoint);
	}

}
