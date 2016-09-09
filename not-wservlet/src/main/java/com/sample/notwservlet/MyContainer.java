package com.sample.notwservlet;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.Response;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.container.InterceptorComponent;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Top level container that pieces together all the parts of the WComponents life cycle.
 *
 * @author Jonathan Austin
 */
public class MyContainer {

	private static final Log LOG = LogFactory.getLog(MyContainer.class);

	/**
	 * @param args arguments
	 */
	public static void main(final String[] args) {

		// Setup the request and response
		Map<String, Serializable> sessionAttributes = new HashMap<>();
		MyContainer container = new MyContainer();
		MyRequest request = new MyRequest(sessionAttributes, "POST");

		StringWriter writer = new StringWriter();
		MyResponse response = new MyResponse(new PrintWriter(writer));
		container.processRequest(request, response);

		System.out.println(writer.toString());

	}

	/**
	 * @param request the request being processed
	 * @param response the response
	 */
	public void processRequest(final Request request, final Response response) {
		try {

			// Check for resource request
			boolean continueProcess = MyContainerUtil.checkResourceRequest(request, response);
			if (!continueProcess) {
				return;
			}

			MyContainerHelper helper = new MyContainerHelper(request, response);

			InterceptorComponent interceptorChain = MyContainerUtil.createInterceptorChain(request);

			WComponent ui = new SampleApp();

			MyContainerUtil.processRequest(helper, ui, interceptorChain);
		} catch (Throwable t) {
			LOG.error("WServlet caught exception.", t);
		} finally {
			LOG.info("...service complete");
		}
	}

}
