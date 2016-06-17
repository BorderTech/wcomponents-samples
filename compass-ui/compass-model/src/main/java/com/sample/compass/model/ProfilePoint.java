package com.sample.compass.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Profile point.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ProfilePoint implements Serializable {


	private final DataType type;
	private final ProfileId profileId;
	private final String classname;
	private final String name;
	private final int childCount;

	public ProfilePoint(final DataType type, final ProfileId profileId, final String classname, final String name, final int childCount) {
		this.type = type;
		this.profileId = profileId;
		this.classname = classname;
		this.name = name;
		this.childCount = childCount > 0 ? childCount : 0;
	}

	public DataType getType() {
		return type;
	}

	public ProfileId getProfileId() {
		return profileId;
	}

	public String getClassname() {
		return classname;
	}

	public String getName() {
		return name;
	}

	public int getChildCount() {
		return childCount;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 29 * hash + Objects.hashCode(this.type);
		hash = 29 * hash + Objects.hashCode(this.profileId);
		hash = 29 * hash + Objects.hashCode(this.classname);
		hash = 29 * hash + Objects.hashCode(this.name);
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
		final ProfilePoint other = (ProfilePoint) obj;
		if (!Objects.equals(this.classname, other.classname)) {
			return false;
		}
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (this.type != other.type) {
			return false;
		}
		return Objects.equals(this.profileId, other.profileId);
	}

}
