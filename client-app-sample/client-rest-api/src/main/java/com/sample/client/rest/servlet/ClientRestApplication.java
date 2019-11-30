package com.sample.client.rest.servlet;

import com.github.bordertech.swagger.application.SwaggerRestApplication;
import com.github.bordertech.swagger.jackson.JacksonObjectMapperProvider;
import com.sample.client.rest.api.ClientServicesResource;

/**
 * Client REST Swagger/Jersey Application.
 */
public class ClientRestApplication extends SwaggerRestApplication {

	/**
	 * Default constructor.
	 */
	public ClientRestApplication() {
		super(ClientServicesResource.class, JacksonObjectMapperProvider.class);
	}
}
