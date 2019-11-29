package com.sample.client.ui.util;

import com.github.bordertech.didums.Didums;
import com.sample.client.services.ClientServicesHelper;
import com.sample.client.services.ClientServicesMockImpl;
import java.io.Serializable;

/**
 * Provide an instance of the client services.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class ClientServicesHelperFactory implements Serializable {

	/**
	 * Singleton instance.
	 */
	private static final ClientServicesHelper INSTANCE = Didums.getService(ClientServicesHelper.class, ClientServicesMockImpl.class);

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
