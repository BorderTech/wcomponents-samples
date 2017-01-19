package com.sample.chart.servlet;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.registry.UIRegistry;
import com.github.bordertech.wcomponents.servlet.WServlet;
import com.sample.chart.DemoApp;

/**
 * Servlet to feed up the application.
 *
 * @author Jonathan Austin
 */
public class DemoServlet extends WServlet {

	/**
	 * @param httpServletRequest the request being processed
	 * @return the Chart application
	 */
	@Override
	public WComponent getUI(final Object httpServletRequest) {
		return UIRegistry.getInstance().getUI(DemoApp.class.getName());
	}

}
