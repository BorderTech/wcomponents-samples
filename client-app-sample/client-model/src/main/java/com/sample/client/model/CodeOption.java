package com.sample.client.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Option with code and description.
 */
public class CodeOption implements Serializable {

	private final String code;
	private final String description;

	/**
	 * @param code the option code
	 * @param description the option description
	 */
	public CodeOption(final String code, final String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * @return the option code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the option description
	 */
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return code;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 29 * hash + Objects.hashCode(this.code);
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
		final CodeOption other = (CodeOption) obj;
		if (!Objects.equals(this.code, other.code)) {
			return false;
		}
		return true;
	}

}
