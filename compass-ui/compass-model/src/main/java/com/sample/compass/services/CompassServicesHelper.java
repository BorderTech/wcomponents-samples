package com.sample.compass.services;

import com.sample.compass.model.DataModel;
import com.sample.compass.model.DataSource;
import com.sample.compass.model.Item;
import com.sample.compass.model.ItemClass;
import com.sample.compass.model.ItemMeta;
import com.sample.compass.model.ItemObject;
import com.sample.compass.model.ProfilePoint;
import com.sample.compass.model.Report;
import java.util.List;

/**
 * Compass Services interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CompassServicesHelper {

	/**
	 * @param profilePoint the profile id to retrieve its classes
	 * @return the classes for this profile point
	 * @throws ServiceException a service exception
	 */
	List<ItemClass> retrieveClasses(final ProfilePoint profilePoint) throws ServiceException;

	/**
	 * @param profilePoint the profile id to retrieve its objects for
	 * @param className the class name of the objects to retrieve
	 * @return the objects for this profile point and matching class name
	 * @throws ServiceException a service exception
	 */
	List<ItemObject> retrieveObjects(final ProfilePoint profilePoint, final String className) throws ServiceException;

	/**
	 *
	 * @param dataSource the profile data source
	 * @return the data for the data source
	 * @throws ServiceException a service exception
	 */
	DataModel retrieveData(final DataSource dataSource) throws ServiceException;

	/**
	 *
	 * @param profileItem the profile id to retrieve the properties for
	 * @return the properties for the profile point
	 * @throws ServiceException a service exception
	 */
	ItemMeta retrieveItemProperties(final Item profileItem) throws ServiceException;

	/**
	 *
	 * @param profileItem the profile id to retrieve the report for
	 * @param reportId the report id
	 * @return the report details
	 * @throws ServiceException a service exception
	 */
	Report retrieveReportDetails(final Item profileItem, final String reportId) throws ServiceException;

}
