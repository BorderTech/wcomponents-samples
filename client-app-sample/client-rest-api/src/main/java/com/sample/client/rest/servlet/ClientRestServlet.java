package com.sample.client.rest.servlet;

import com.github.bordertech.swagger.servlet.SwaggerJersey2Servlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Client swagger jersey servlet.
 */
@WebServlet(urlPatterns = "/api/*",
		initParams
		= {
			// Tell Jersey to use JSON
			@WebInitParam(name = "com.sun.jersey.api.json.POJOMappingFeature", value = "true")
			,
			// Tell Jersey the Application
			@WebInitParam(name = "javax.ws.rs.Application", value = "com.sample.client.rest.servlet.ClientRestApplication")
		},
		loadOnStartup = 1
)
@SuppressWarnings("NoWhitespaceBefore")
public class ClientRestServlet extends SwaggerJersey2Servlet {
}
