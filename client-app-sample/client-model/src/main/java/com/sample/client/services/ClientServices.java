package com.sample.client.services;

import com.sample.client.model.ClientSummary;
import com.sample.client.model.CodeOption;
import com.sample.client.model.DocumentContent;
import com.sample.client.model.DocumentDetail;
import com.sample.client.model.IndividualDetail;
import com.sample.client.model.OrganisationDetail;
import com.sample.client.services.exception.ClientNotFoundException;
import com.sample.client.services.exception.DocumentNotFoundException;
import com.sample.client.services.exception.ServiceException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Client Services interface.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ClientServices {

	/**
	 * Retrieve table names.
	 *
	 * @return the list of table names
	 * @throws ServiceException a service exception
	 */
	List<String> retrieveTables() throws ServiceException;

	/**
	 * Retrieve the codes for a table.
	 *
	 * @param table the table name to retrieve
	 * @return the list of code options
	 * @throws ServiceException a service exception
	 */
	List<CodeOption> retrieveCodes(String table) throws ServiceException;

	/**
	 * Search origanisation and individual clients.
	 *
	 * @param search the search criteria
	 * @return the list of clients
	 * @throws ServiceException a service exception
	 */
	List<ClientSummary> searchClients(String search) throws ServiceException;

	/**
	 * Search individual clients.
	 *
	 * @param search the search criteria
	 * @return the list of individuals
	 * @throws ServiceException a service exception
	 */
	List<IndividualDetail> searchIndividuals(String search) throws ServiceException;

	/**
	 * Retrieve an individual.
	 *
	 * @param clientId the client id to retrieve
	 * @return the details
	 * @throws ServiceException a service exception
	 * @throws ClientNotFoundException client not found
	 */
	IndividualDetail retrieveIndividual(String clientId) throws ServiceException, ClientNotFoundException;

	/**
	 * Create an individual.
	 *
	 * @param detail the details to create
	 * @return the created individual
	 * @throws ServiceException a service exception
	 */
	IndividualDetail createIndividual(IndividualDetail detail) throws ServiceException;

	/**
	 * Update an individual.
	 *
	 * @param detail the updated details
	 * @return the updated individual
	 * @throws ServiceException a service exception
	 */
	IndividualDetail updateIndividual(IndividualDetail detail) throws ServiceException;

	/**
	 * Delete an individual.
	 *
	 * @param clientId the client Id to delete
	 * @throws ServiceException a service exception
	 */
	void deleteIndividual(String clientId) throws ServiceException;

	/**
	 * Search origanisations.
	 *
	 * @param search the search criteria
	 * @return the list of organisations
	 * @throws ServiceException a service exception
	 */
	List<OrganisationDetail> searchOrganisations(String search) throws ServiceException;

	/**
	 * Retrieve an organisation.
	 *
	 * @param clientId the client id to retrieve
	 * @return the details
	 * @throws ServiceException a service exception
	 * @throws ClientNotFoundException client not found
	 */
	OrganisationDetail retrieveOrganisation(String clientId) throws ServiceException, ClientNotFoundException;

	/**
	 * Create an organisation.
	 *
	 * @param detail the details to create
	 * @return the created organisation
	 * @throws ServiceException a service exception
	 */
	OrganisationDetail createOrganisation(OrganisationDetail detail) throws ServiceException;

	/**
	 * Update an organisation.
	 *
	 * @param detail the updated details
	 * @return the updated organisation
	 * @throws ServiceException a service exception
	 */
	OrganisationDetail updateOrganisation(OrganisationDetail detail) throws ServiceException;

	/**
	 * Delete an organisation.
	 *
	 * @param clientId the client Id to delete
	 * @throws ServiceException a service exception
	 */
	void deleteOrganisation(String clientId) throws ServiceException;

	/**
	 * Retrieve client documents.
	 *
	 * @param clientId the client id to retrieve documents for
	 * @return the client documents
	 * @throws ServiceException a service exception
	 * @throws ClientNotFoundException client not found
	 */
	List<DocumentDetail> retrieveClientDocuments(String clientId) throws ServiceException, ClientNotFoundException;

	/**
	 * Retrieve document contents.
	 *
	 * @param documentId the document id
	 * @return the document content
	 * @throws ServiceException a service exception
	 * @throws DocumentNotFoundException document not found exception
	 */
	DocumentContent retrieveDocument(String documentId) throws ServiceException, DocumentNotFoundException;

	/**
	 * Retrieve conversion rate.
	 *
	 * @param fromCurrency from currency code
	 * @param toCurrency to currency code
	 * @return the conversion rate
	 * @throws ServiceException a service exception
	 */
	BigDecimal retrieveConversionRate(String fromCurrency, String toCurrency) throws ServiceException;

}
