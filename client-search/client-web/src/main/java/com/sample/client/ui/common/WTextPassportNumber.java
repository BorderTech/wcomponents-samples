package com.sample.client.ui.common;

import com.github.bordertech.wcomponents.WText;

/**
 * Display the passport number.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class WTextPassportNumber extends WText {

	/**
	 * Construct country code.
	 */
	public WTextPassportNumber() {
		setBeanProperty("passportNumber");
	}
}
