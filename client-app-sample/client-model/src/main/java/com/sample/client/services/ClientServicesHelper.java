package com.sample.client.services;

import com.sample.client.model.ClientSummary;
import com.sample.client.model.CodeOption;
import com.sample.client.model.DocumentDetail;
import com.sample.client.model.IndividualDetail;
import com.sample.client.model.OrganisationDetail;
import java.math.BigDecimal;
import java.util.List;

/**
 * Client Services interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ClientServicesHelper {

	/**
	 *
	 * @param search the search criteria
	 * @return the list of clients
	 * @throws ServiceException a service exception
	 */
	List<ClientSummary> searchClients(final String search) throws ServiceException;

	/**
	 *
	 * @param table the table to retrieve
	 * @return the list of code options
	 * @throws ServiceException a service exception
	 */
	List<CodeOption> retrieveCodes(final String table) throws ServiceException;

	/**
	 *
	 * @param clientId the client id to retrieve
	 * @return the details
	 * @throws ServiceException a service exception
	 * @throws ClientNotFoundException client not found
	 */
	IndividualDetail retrieveIndividual(final String clientId) throws ServiceException, ClientNotFoundException;

	/**
	 *
	 * @param detail the details to create
	 * @return the client id
	 * @throws ServiceException a service exception
	 */
	String createIndividual(final IndividualDetail detail) throws ServiceException;

	/**
	 *
	 * @param detail the updated details
	 * @throws ServiceException a service exception
	 */
	void updateIndividual(final IndividualDetail detail) throws ServiceException;

	/**
	 *
	 * @param clientId the client id to retrieve
	 * @return the details
	 * @throws ServiceException a service exception
	 * @throws ClientNotFoundException client not found
	 */
	OrganisationDetail retrieveOrganisation(final String clientId) throws ServiceException, ClientNotFoundException;

	/**
	 *
	 * @param detail the details to create
	 * @return the client id
	 * @throws ServiceException a service exception
	 */
	String createOrganisation(final OrganisationDetail detail) throws ServiceException;

	/**
	 *
	 * @param detail the updated details
	 * @throws ServiceException a service exception
	 */
	void updateOrganisation(final OrganisationDetail detail) throws ServiceException;

	/**
	 *
	 * @param fromCurrency from currency code
	 * @param toCurrency to currency code
	 * @return the conversion rate
	 */
	BigDecimal retrieveConversionRate(final String fromCurrency, final String toCurrency);

	/**
	 *
	 * @param clientId the client id to retrieve documents for
	 * @return the client documents
	 * @throws ServiceException a service exception
	 * @throws ClientNotFoundException client not found
	 */
	List<DocumentDetail> retrieveClientDocuments(final String clientId) throws ServiceException, ClientNotFoundException;

}
