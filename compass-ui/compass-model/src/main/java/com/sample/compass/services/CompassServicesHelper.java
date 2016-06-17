package com.sample.compass.services;

import com.sample.compass.model.DataModel;
import com.sample.compass.model.DataSource;
import com.sample.compass.model.ItemMeta;
import com.sample.compass.model.Item;
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
	 * @param profileItem the profile id to retrieve its children for
	 * @return the children profile points
	 * @throws ServiceException a service exception
	 */
	List<Item> retrieveProfileItems(final Item profileItem) throws ServiceException;

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
