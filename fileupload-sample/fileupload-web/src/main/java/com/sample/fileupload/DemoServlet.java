package com.sample.fileupload;

import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.registry.UIRegistry;
import com.github.bordertech.wcomponents.servlet.WServlet;

/**
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DemoServlet extends WServlet {

	@Override
	public WComponent getUI(final Object httpServletRequest) {
		return UIRegistry.getInstance().getUI(DemoApp.class.getName());
	}

}
