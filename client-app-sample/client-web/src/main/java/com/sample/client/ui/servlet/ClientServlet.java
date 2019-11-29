package com.sample.client.ui.servlet;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.registry.UIRegistry;
import com.github.bordertech.wcomponents.servlet.WServlet;
import com.sample.client.ui.application.ClientApp;

/**
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ClientServlet extends WServlet {

	@Override
	public WComponent getUI(final Object httpServletRequest) {
		return UIRegistry.getInstance().getUI(ClientApp.class.getName());
	}

}
