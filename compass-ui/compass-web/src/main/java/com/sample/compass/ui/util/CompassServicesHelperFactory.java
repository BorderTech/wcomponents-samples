package com.sample.compass.ui.util;

import com.github.bordertech.wcomponents.util.Factory;
import com.sample.compass.services.CompassServicesHelper;
import java.io.Serializable;

/**
 * Provide an instance of the compass services.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class CompassServicesHelperFactory implements Serializable {

	/**
	 * Default serialisation identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Singleton instance.
	 */
	private static final CompassServicesHelper INSTANCE = (CompassServicesHelper) Factory.newInstance(CompassServicesHelper.class);

	/**
	 * Don't allow external instantiation of this class.
	 */
	private CompassServicesHelperFactory() {
		// Do nothing
	}

	/**
	 * @return the singleton instance of the CRM Services.
	 */
	public static CompassServicesHelper getInstance() {
		return INSTANCE;
	}
}
