package com.sample.client.rest.model;

import com.github.bordertech.restfriends.envelope.DataEnvelope;
import java.math.BigDecimal;

/**
 * Convert currency response.
 */
public class CurrencyResponse implements DataEnvelope<BigDecimal> {

	private BigDecimal data;

	/**
	 * Default constructor.
	 */
	public CurrencyResponse() {
	}

	/**
	 * @param data the currency conversion
	 */
	public CurrencyResponse(final BigDecimal data) {
		this.data = data;
	}

	@Override
	public BigDecimal getData() {
		return data;
	}

	@Override
	public void setData(final BigDecimal data) {
		this.data = data;
	}

}
