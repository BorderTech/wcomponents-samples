package com.github.bordertech.wcomponents.polling;

/**
 * Service result holder.
 * <p>
 * The result can be an exception or the result.
 * </p>
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ResultHolder {

	private Object result;

	/**
	 * @return the polling result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @param result the polling result
	 */
	public void setResult(final Object result) {
		this.result = result;
	}

}
