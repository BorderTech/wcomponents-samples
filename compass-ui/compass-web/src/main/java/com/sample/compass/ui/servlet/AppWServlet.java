package com.sample.compass.ui.servlet;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.registry.UIRegistry;
import com.github.bordertech.wcomponents.servlet.WServlet;
import com.sample.compass.ui.application.CompassApp;

/**
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AppWServlet extends WServlet {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WComponent getUI(final Object httpServletRequest) {
		return UIRegistry.getInstance().getUI(CompassApp.class.getName());
	}

}
