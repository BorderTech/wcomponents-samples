package com.sample.compass.services;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception processing services.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ServiceException extends Exception {

	/**
	 * Default serialisation identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Error messages.
	 */
	private final List<ExceptionDetail> errors = new ArrayList<>();

	/**
	 * @param msg exception message
	 */
	public ServiceException(final String msg) {
		this(null, msg);
	}

	/**
	 * @param code the exception code
	 * @param msg exception message
	 */
	public ServiceException(final String code, final String msg) {
		super(msg);
		addError(code, msg);
	}

	/**
	 * @param msg exception message
	 * @param throwable original exception
	 */
	public ServiceException(final String msg, final Throwable throwable) {
		this(null, msg, throwable);
	}

	/**
	 * @param code the exception code
	 * @param msg the exception message
	 * @param throwable original exception
	 */
	public ServiceException(final String code, final String msg, final Throwable throwable) {
		super(msg, throwable);
		addError(code, msg);
	}

	/**
	 * @return the error messages
	 */
	public List<ExceptionDetail> getErrorMessages() {
		return errors;
	}

	/**
	 * Add error message.
	 *
	 * @param msg error message
	 */
	public void addError(final String msg) {
		addError(null, msg);
	}

	/**
	 * Add error message with a code.
	 *
	 * @param code error code
	 * @param msg error message
	 */
	public void addError(final String code, final String msg) {
		errors.add(new ExceptionDetail(code, msg));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getMessage() {
		return toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (ExceptionDetail msg : getErrorMessages()) {
			if (buffer.length() > 0) {
				buffer.append(" : ");
			}
			buffer.append(msg);
		}
		return buffer.toString();
	}

}
