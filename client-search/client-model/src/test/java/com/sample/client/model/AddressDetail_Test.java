package com.sample.client.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Example test.
 *
 * @author Jonathan Austin
 */
public class AddressDetail_Test {

	@Test
	public void testCountryCodeAccessors() {
		AddressDetail detail = new AddressDetail();
		Assert.assertNull("Should be null by default", detail.getCountryCode());
		detail.setCountryCode("X");
		Assert.assertEquals("Should be X", "X", detail.getCountryCode());
	}

}
