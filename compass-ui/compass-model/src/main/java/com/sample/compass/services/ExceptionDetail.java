package com.sample.compass.services;

import java.io.Serializable;

/**
 * Exception detail.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ExceptionDetail implements Serializable {

	private final String code;
	private final String desc;

	/**
	 * @param desc the exception description
	 */
	public ExceptionDetail(final String desc) {
		this.code = null;
		this.desc = desc;
	}

	/**
	 *
	 * @param code the exception code
	 * @param desc the exception description
	 */
	public ExceptionDetail(final String code, final String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 *
	 * @return the exception code
	 */
	public String getCode() {
		return code;
	}

	/**
	 *
	 * @return the exception description
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return getDesc();
	}
}
