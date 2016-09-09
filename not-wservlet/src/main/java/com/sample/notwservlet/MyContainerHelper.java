package com.sample.notwservlet;

import com.github.bordertech.wcomponents.Environment;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.Response;
import com.github.bordertech.wcomponents.UIContext;
import com.github.bordertech.wcomponents.container.AbstractContainerHelper;
import com.github.bordertech.wcomponents.servlet.WServlet;
import com.github.bordertech.wcomponents.util.ConfigurationProperties;
import com.github.bordertech.wcomponents.util.SystemException;
import com.github.bordertech.wcomponents.util.Util;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The helper for {@link MyContainer}. These helpers provide common functionality between containers.
 *
 * @author Jonathan Austin
 */
public class MyContainerHelper extends AbstractContainerHelper {

	private static final Log LOG = LogFactory.getLog(MyContainerHelper.class);

	private final String targetComponentId;

	private final boolean dataRequest;

	private final Request request;

	private final Response response;

	private UIContext uiContext;

	/**
	 * @param request the request being processed
	 * @param response the response
	 */
	public MyContainerHelper(final Request request, final Response response) {

		this.request = request;
		this.response = response;
		this.dataRequest = request.getParameter(WServlet.DATA_LIST_PARAM_NAME) != null;

		String[] target = request.getParameterValues(WServlet.AJAX_TRIGGER_PARAM_NAME);
		if (target == null) {
			target = request.getParameterValues(WServlet.TARGET_ID_PARAM_NAME);
		}
		this.targetComponentId = target == null ? null : target[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UIContext getUIContext() {
		return uiContext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setUIContext(final UIContext uiContext) {
		this.uiContext = uiContext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected UIContext createUIContext() {
		// FIXME Should be in the abstract
		if (targetComponentId != null) {
			throw new SystemException("AJAX request for a session with no context set");
		}
		return super.createUIContext();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Environment createEnvironment() {
		MyEnvironment env = new MyEnvironment();
		return env;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Request createRequest() {
		return request;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PrintWriter getPrintWriter() throws IOException {
		return response.getWriter();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Response getResponse() {
		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void invalidateSession() {
		uiContext = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void redirectForLogout() {
		// FIXME Should be in the abstract
		String url = ConfigurationProperties.getLogoutUrl();

		if (Util.empty(url)) {
			LOG.warn("No logout URL specified");

			try {
				getResponse().getWriter().write("Logged out successfully");
			} catch (IOException e) {
				LOG.error("Failed to send logout message", e);
			}
		} else {
			try {
				getResponse().sendRedirect(url);
			} catch (IOException e) {
				LOG.error("Failed to redirect to logout url " + url, e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleError(final Throwable error) throws IOException {
		// FIXME Should be in the abstract
		if (targetComponentId != null || dataRequest) {
			getResponse().sendError(500, "Internal Error");
		} else {
			super.handleError(error);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setTitle(final String title) {
		// Do something
	}

	@Override
	protected void updateEnvironment(final Environment env) {
		// Do something
	}

	@Override
	protected void updateRequest(final Request request) {
		// Do something
	}
}
