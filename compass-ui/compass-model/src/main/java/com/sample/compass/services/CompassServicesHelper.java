package com.sample.compass.services;

import com.sample.compass.model.DataCriteria;
import com.sample.compass.model.ProfileData;
import com.sample.compass.model.ProfileId;
import com.sample.compass.model.ProfilePoint;
import java.util.List;

/**
 * Compass Services interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface CompassServicesHelper {

	/**
	 * @param profileId the profile id to retrieve its children for
	 * @return the children profile points
	 * @throws ServiceException a service exception
	 */
	List<ProfilePoint> retrieveProfilePoints(final ProfileId profileId) throws ServiceException;

	/**
	 *
	 * @param criteria the profile criteria
	 * @return the data for the profile criteria
	 * @throws ServiceException a service exception
	 */
	ProfileData retrieveProfileData(final DataCriteria criteria) throws ServiceException;

	/**
	 *
	 * @param profileId the profile id to retrieve the properties for
	 * @return the properties for the profile point
	 * @throws ServiceException a service exception
	 */
	ProfileData retrieveProfileProperties(final ProfileId profileId) throws ServiceException;

}
