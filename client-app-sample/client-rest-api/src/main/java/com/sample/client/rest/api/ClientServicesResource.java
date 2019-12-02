package com.sample.client.rest.api;

import com.github.bordertech.didums.Didums;
import com.github.bordertech.restfriends.envelope.impl.ErrorResponse;
import com.github.bordertech.restfriends.exception.RestBusinessException;
import com.sample.client.model.ClientSummary;
import com.sample.client.model.CodeOption;
import com.sample.client.model.DocumentContent;
import com.sample.client.model.DocumentDetail;
import com.sample.client.model.IndividualDetail;
import com.sample.client.model.OrganisationDetail;
import com.sample.client.rest.model.CurrencyResponse;
import com.sample.client.rest.model.DocumentContentResponse;
import com.sample.client.rest.model.IndividualDetailResponse;
import com.sample.client.rest.model.OrganisationDetailResponse;
import com.sample.client.rest.model.RetrieveClientDocumentsResponse;
import com.sample.client.rest.model.RetrieveCodesResponse;
import com.sample.client.rest.model.RetrieveTablesResponse;
import com.sample.client.rest.model.SearchClientsResponse;
import com.sample.client.rest.model.SearchIndividualsResponse;
import com.sample.client.rest.model.SearchOrganisationsResponse;
import com.sample.client.services.ClientServices;
import com.sample.client.services.ClientServicesMockImpl;
import com.sample.client.services.exception.ClientNotFoundException;
import com.sample.client.services.exception.DocumentNotFoundException;
import com.sample.client.services.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Sample REST Resource call a backing service implementation.
 */
@Api(value = "ClientServices")
@Path(value = "v1")
@ApiResponses(value = {
	@ApiResponse(code = 400, message = "Invalid request", response = ErrorResponse.class)
	,
	@ApiResponse(code = 500, message = "Internal error", response = ErrorResponse.class)
})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@SuppressWarnings("NoWhitespaceBefore")
public class ClientServicesResource {

	private final ClientServices backing = Didums.getService(ClientServices.class, ClientServicesMockImpl.class);

	/**
	 * Retrieve table names.
	 *
	 * @return the table names
	 * @throws RestBusinessException a business exception
	 */
	@GET
	@Path("tables")
	@ApiOperation(value = "Retrieve table names")
	public RetrieveTablesResponse retrieveTables()
			throws RestBusinessException {
		try {
			List<String> resp = backing.retrieveTables();
			return new RetrieveTablesResponse(new ArrayList<>(resp));
		} catch (ServiceException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Retrieve table codes.
	 *
	 * @param table the table name
	 * @return the table codes
	 * @throws RestBusinessException a business exception
	 */
	@GET
	@Path("tables/{table}")
	@ApiOperation(value = "Retrieve codes for a table")
	public RetrieveCodesResponse retrieveCodes(
			@ApiParam(value = "Table name") @PathParam("table") final String table)
			throws RestBusinessException {
		try {
			List<CodeOption> resp = backing.retrieveCodes(table);
			return new RetrieveCodesResponse(new ArrayList<>(resp));
		} catch (ServiceException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Search clients (Individuals and Orgs).
	 *
	 * @param search the search criteria
	 * @return the matching clients
	 * @throws RestBusinessException a business exception
	 */
	@GET
	@Path("clients")
	@ApiOperation(value = "Search clients including Individuals and Organisations")
	public SearchClientsResponse searchClients(
			@ApiParam(value = "Search criteria") @QueryParam("search") final String search)
			throws RestBusinessException {
		try {
			List<ClientSummary> resp = backing.searchClients(search);
			return new SearchClientsResponse(new ArrayList<>(resp));
		} catch (ServiceException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Search individuals.
	 *
	 * @param search the search criteria
	 * @return the matching clients
	 * @throws RestBusinessException a business exception
	 */
	@GET
	@Path("individuals")
	@ApiOperation(value = "Search individuals")
	public SearchIndividualsResponse searchIndividuals(
			@ApiParam(value = "Search criteria") @QueryParam("search") final String search)
			throws RestBusinessException {
		try {
			List<IndividualDetail> resp = backing.searchIndividuals(search);
			return new SearchIndividualsResponse(new ArrayList<>(resp));
		} catch (ServiceException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Retrieve individual.
	 *
	 * @param clientId the client id
	 * @return the individual details
	 * @throws RestBusinessException a business exception
	 */
	@GET
	@Path("individuals/{id}")
	@ApiOperation(value = "Retrieve individual")
	public IndividualDetailResponse retrieveIndividual(
			@ApiParam(value = "Client id") @PathParam("id") final String clientId)
			throws RestBusinessException {
		try {
			IndividualDetail resp = backing.retrieveIndividual(clientId);
			return new IndividualDetailResponse(resp);
		} catch (ServiceException | ClientNotFoundException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Create individual.
	 *
	 * @param detail the individual details
	 * @return the individual details
	 * @throws RestBusinessException a business exception
	 */
	@POST
	@Path("individuals")
	@ApiOperation(value = "Create individual")
	public IndividualDetailResponse createIndividual(
			@ApiParam(value = "Individual details") final IndividualDetail detail) throws RestBusinessException {
		try {
			IndividualDetail resp = backing.createIndividual(detail);
			return new IndividualDetailResponse(resp);
		} catch (ServiceException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Update individual.
	 *
	 * @param clientId the client id
	 * @param detail the individual details
	 * @return the individual details
	 * @throws RestBusinessException a business exception
	 */
	@PUT
	@Path("individuals/{id}")
	@ApiOperation(value = "Update individual")
	public IndividualDetailResponse updateIndividual(
			@ApiParam(value = "Client id") @PathParam("id") final String clientId,
			@ApiParam(value = "Individual details") final IndividualDetail detail)
			throws RestBusinessException {
		// Check IDs
		if (!Objects.equals(clientId, detail.getClientId())) {
			throw new RestBusinessException("Client ID does not match ID in details.");
		}
		try {
			IndividualDetail resp = backing.updateIndividual(detail);
			return new IndividualDetailResponse(resp);
		} catch (ServiceException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Delete individuals.
	 *
	 * @param clientId the client id
	 * @return the OK response
	 * @throws RestBusinessException a business exception
	 */
	@DELETE
	@Path("individuals/{id}")
	@ApiOperation(value = "Delete individual")
	public Response deleteIndividual(
			@ApiParam(value = "Client id") @PathParam("id") final String clientId)
			throws RestBusinessException {
		try {
			backing.deleteIndividual(clientId);
			return Response.ok().build();
		} catch (ServiceException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Search organisations.
	 *
	 * @param search the search criteria
	 * @return the matching clients
	 * @throws RestBusinessException a business exception
	 */
	@GET
	@Path("organisations")
	@ApiOperation(value = "Search organisations")
	public SearchOrganisationsResponse searchOrganisations(
			@ApiParam(value = "Search criteria") @QueryParam("search") final String search)
			throws RestBusinessException {
		try {
			List<OrganisationDetail> resp = backing.searchOrganisations(search);
			return new SearchOrganisationsResponse(new ArrayList<>(resp));
		} catch (ServiceException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Retrieve organisation.
	 *
	 * @param clientId the client id
	 * @return the organisation details
	 * @throws RestBusinessException a business exception
	 */
	@GET
	@Path("organisations/{id}")
	@ApiOperation(value = "Retrieve individual")
	public OrganisationDetailResponse retrieveOrganisation(
			@ApiParam(value = "Client id") @PathParam("id") final String clientId)
			throws RestBusinessException {
		try {
			OrganisationDetail resp = backing.retrieveOrganisation(clientId);
			return new OrganisationDetailResponse(resp);
		} catch (ServiceException | ClientNotFoundException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Create organisation.
	 *
	 * @param detail the organisation details
	 * @return the organisation details
	 * @throws RestBusinessException a business exception
	 */
	@POST
	@Path("organisations")
	@ApiOperation(value = "Create organisation")
	public OrganisationDetailResponse createOrganisation(
			@ApiParam(value = "Organisation details") final OrganisationDetail detail)
			throws RestBusinessException {
		try {
			OrganisationDetail resp = backing.createOrganisation(detail);
			return new OrganisationDetailResponse(resp);
		} catch (ServiceException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Update organisation.
	 *
	 * @param clientId the client id
	 * @param detail the organisation details
	 * @return the organisation details
	 * @throws RestBusinessException a business exception
	 */
	@PUT
	@Path("organisations/{id}")
	@ApiOperation(value = "Update organisation")
	public OrganisationDetailResponse updateOrganisation(
			@ApiParam(value = "Client id") @PathParam("id") final String clientId,
			@ApiParam(value = "Organisation details") final OrganisationDetail detail)
			throws RestBusinessException {
		// Check IDs
		if (!Objects.equals(clientId, detail.getClientId())) {
			throw new RestBusinessException("Client ID does not match ID in details.");
		}
		try {
			OrganisationDetail resp = backing.updateOrganisation(detail);
			return new OrganisationDetailResponse(resp);
		} catch (ServiceException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Delete organisation.
	 *
	 * @param clientId the client id
	 * @return the OK response
	 * @throws RestBusinessException a business exception
	 */
	@DELETE
	@Path("organisations/{id}")
	@ApiOperation(value = "Delete organisation")
	public Response deleteOrganisation(
			@ApiParam(value = "Client id") @PathParam("id") final String clientId)
			throws RestBusinessException {
		try {
			backing.deleteOrganisation(clientId);
			return Response.ok().build();
		} catch (ServiceException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Retrieve client documents.
	 *
	 * @param clientId the client id
	 * @return the client documents
	 * @throws RestBusinessException a business exception
	 */
	@GET
	@Path("clients/{id}/documents")
	@ApiOperation(value = "Retrieve client documents")
	public RetrieveClientDocumentsResponse retrieveClientDocuments(
			@ApiParam(value = "Client id") @PathParam("id") final String clientId)
			throws RestBusinessException {
		try {
			List<DocumentDetail> resp = backing.retrieveClientDocuments(clientId);
			return new RetrieveClientDocumentsResponse(new ArrayList<>(resp));
		} catch (ServiceException | ClientNotFoundException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Retrieve client document content.
	 *
	 * @param documentId the document id
	 * @return the client documents
	 * @throws RestBusinessException a business exception
	 */
	@GET
	@Path("documents/{docId}")
	@ApiOperation(value = "Retrieve client documents")
	public DocumentContentResponse retrieveDocument(
			@ApiParam(value = "Document id") @PathParam("docId") final String documentId)
			throws RestBusinessException {
		try {
			DocumentContent resp = backing.retrieveDocument(documentId);
			return new DocumentContentResponse(resp);
		} catch (ServiceException | DocumentNotFoundException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

	/**
	 * Retrieve currency conversion rate.
	 *
	 * @param fromCurrency the from country code
	 * @param toCurrency the to country code
	 * @return the conversion rate
	 * @throws RestBusinessException a business exception
	 */
	@GET
	@Path("rates")
	@ApiOperation(value = "Retrieve conversion rates")
	public CurrencyResponse retrieveConversionRate(
			@ApiParam(value = "From country code", required = true) @QueryParam("from") final String fromCurrency,
			@ApiParam(value = "To country code", required = true) @QueryParam("to") final String toCurrency) throws RestBusinessException {
		try {
			BigDecimal resp = backing.retrieveConversionRate(fromCurrency, toCurrency);
			return new CurrencyResponse(resp);
		} catch (ServiceException e) {
			throw new RestBusinessException(e.getMessage(), e);
		}
	}

}
