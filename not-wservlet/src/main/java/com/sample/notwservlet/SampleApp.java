package com.sample.notwservlet;

import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WText;

/**
 * Sample application.
 *
 * @author Jonathan Austin
 */
public class SampleApp extends WApplication {

	/**
	 * Creates a new Sample App.
	 */
	public SampleApp() {

		add(new WText("Hello World"));
	}
}
