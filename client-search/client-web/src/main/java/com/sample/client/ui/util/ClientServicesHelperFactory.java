package com.sample.client.ui.util;

import com.github.bordertech.wcomponents.util.Factory;
import com.sample.client.services.ClientServicesHelper;
import java.io.Serializable;

/**
 * Provide an instance of the client services.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ClientServicesHelperFactory implements Serializable {

	/**
	 * Default serialisation identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Singleton instance.
	 */
	private static final ClientServicesHelper INSTANCE = (ClientServicesHelper) Factory.newInstance(ClientServicesHelper.class);

	/**
	 * Don't allow external instantiation of this class.
	 */
	private ClientServicesHelperFactory() {
		// Do nothing
	}

	/**
	 * @return the singleton instance of the CRM Services.
	 */
	public static ClientServicesHelper getInstance() {
		return INSTANCE;
	}
}
