package com.sample.fileupload.servlet;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.registry.UIRegistry;
import com.github.bordertech.wcomponents.servlet.WServlet;
import com.sample.fileupload.DemoApp;

/**
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DemoServlet extends WServlet {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WComponent getUI(final Object httpServletRequest) {
		return UIRegistry.getInstance().getUI(DemoApp.class.getName());
	}

}
