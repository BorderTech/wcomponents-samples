package com.sample.fileupload;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WApplication;

/**
 * The root WComponent application for the Sample web UI. JSR166, WorkManager, JSR246, JSR237, ExecutorService, Future,
 * WAS 8.5.5.0
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DemoApp extends WApplication {

	/**
	 * Creates a new Sample App.
	 */
	public DemoApp() {
	}

	@Override
	protected void preparePaintComponent(Request request) {
		// Just here for PMD and Checkstyle warnings
		super.preparePaintComponent(request);
	}

}
