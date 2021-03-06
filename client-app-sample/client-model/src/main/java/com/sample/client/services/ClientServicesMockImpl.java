package com.sample.client.services;

import com.sample.client.model.AddressDetail;
import com.sample.client.model.ClientSummary;
import com.sample.client.model.ClientType;
import com.sample.client.model.CodeOption;
import com.sample.client.model.DocumentContent;
import com.sample.client.model.DocumentDetail;
import com.sample.client.model.IndividualDetail;
import com.sample.client.model.OrganisationDetail;
import com.sample.client.model.PassportDetail;
import com.sample.client.model.StateType;
import com.sample.client.services.exception.ClientNotFoundException;
import com.sample.client.services.exception.DocumentNotFoundException;
import com.sample.client.services.exception.ServiceException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.io.IOUtils;

/**
 * Mock Client Services.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ClientServicesMockImpl implements ClientServicesHelper {

	private static final Set<IndividualDetail> INDIVIDUALS = new HashSet<>();

	private static final Set<OrganisationDetail> ORGANISATIONS = new HashSet<>();

	private static final Map<String, DocumentDetail> DOCUMENTS = createDocuments();

	static {
		for (int i = 1; i < 10; i++) {
			INDIVIDUALS.add(createIndividual(i));
		}

		for (int i = 1; i < 10; i++) {
			ORGANISATIONS.add(createOrganisation(i));
		}

	}

	/**
	 *
	 * @param idx the suffix
	 * @return the detail
	 */
	private static IndividualDetail createIndividual(final int idx) {
		IndividualDetail detail = new IndividualDetail();
		detail.setClientId("INDIV" + idx);
		detail.setDateOfBirth(createDob(idx));
		detail.setEmail("eg@example.com");
		detail.setFirstName("first" + idx);
		detail.setLastName("last" + idx);
		detail.getIdentifications().add(createPassport(idx));
		detail.setAddress(createAddress(idx));
		return detail;
	}

	/**
	 *
	 * @param idx the suffix
	 * @return the detail
	 */
	private static OrganisationDetail createOrganisation(final int idx) {
		OrganisationDetail detail = new OrganisationDetail();
		detail.setClientId("ORG" + idx);
		detail.setEmail("eg@example.com");
		detail.setAddress(createAddress(idx));
		detail.setAbn("ABN" + idx);
		detail.setName("Name" + idx);
		return detail;
	}

	/**
	 *
	 * @param idx the suffix
	 * @return the detail
	 */
	private static PassportDetail createPassport(final int idx) {
		PassportDetail detail = new PassportDetail();
		detail.setCountryCode("A");
		detail.setPassportNumber("PASS" + idx);
		return detail;
	}

	/**
	 *
	 * @param idx the suffix
	 * @return the DOB
	 */
	private static Date createDob(final int idx) {
		Calendar cal = Calendar.getInstance();
		cal.set(1980, 10, idx, 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 *
	 * @param idx the suffix
	 * @return the detail
	 */
	private static AddressDetail createAddress(final int idx) {
		AddressDetail detail = new AddressDetail();
		detail.setCountryCode("A");
		detail.setPostcode("2000");
		detail.setState(StateType.TAS);
		detail.setStreet("street" + idx);
		detail.setSuburb("suburb" + idx);
		return detail;
	}

	/**
	 *
	 * @param detail the detail
	 * @return the summary
	 */
	private static ClientSummary orgToSummary(final OrganisationDetail detail) {
		ClientSummary summary = new ClientSummary(detail.getClientId(), ClientType.Organisation);
		if (detail.getAddress() != null) {
			summary.setAddress(detail.getAddress().toString());
		}
		summary.setName(detail.getName());
		return summary;
	}

	/**
	 *
	 * @param detail the detail
	 * @return the summary
	 */
	private static ClientSummary indivToSummary(final IndividualDetail detail) {
		ClientSummary summary = new ClientSummary(detail.getClientId(), ClientType.Individual);
		if (detail.getAddress() != null) {
			summary.setAddress(detail.getAddress().toString());
		}
		summary.setName(detail.getFirstName() + " " + detail.getLastName());
		if (detail.getIdentifications() != null) {
			summary.setIdentifications(new ArrayList<PassportDetail>(detail.getIdentifications()));
		}
		return summary;
	}

	private static Map<String, DocumentDetail> createDocuments() {
		// Build mock list of document details
		Map<String, DocumentDetail> docs = new HashMap<>();
		int idx = 0;
		for (String name : new String[]{"Einstein", "Bohr", "Maxwell", "Curie", "Schrodinger", "Feynman", "Young", "Roentgen"}) {
			DocumentDetail doc = createImageDocument(idx++, name);
			docs.put(doc.getDocumentId(), doc);
		}
		for (String name : new String[]{"document1", "document2"}) {
			DocumentDetail doc = createWordDocument(idx++, name);
			docs.put(doc.getDocumentId(), doc);
		}
		for (String name : new String[]{"sample1", "sample2"}) {
			DocumentDetail doc = createPdfDocument(idx++, name);
			docs.put(doc.getDocumentId(), doc);
		}
		return docs;
	}

	private static DocumentDetail createImageDocument(final int idx, final String name) {
		return new DocumentDetail(createId(idx), name, createDate(idx), "/sample/images/" + name + ".jpg");
	}

	private static DocumentDetail createPdfDocument(final int idx, final String name) {
		return new DocumentDetail(createId(idx), name, createDate(idx), "/sample/docs/" + name + ".pdf");
	}

	private static DocumentDetail createWordDocument(final int idx, final String name) {
		return new DocumentDetail(createId(idx), name, createDate(idx), "/sample/docs/" + name + ".docx");
	}

	private static String createId(final int idx) {
		return "doc-" + new DecimalFormat("000").format(idx);
	}

	private static Date createDate(final int idx) {
		int yr = 2010 - (idx % 4);
		int mth = 12 - (idx % 3);
		int day = 28 - (idx % 7);
		Calendar dt = Calendar.getInstance();
		dt.set(yr, mth, day);
		return dt.getTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ClientSummary> searchClients(final String search) throws ServiceException {

		if ("error".equals(search)) {
			throw new ServiceException("Mock error");
		}

		if ("none".equals(search)) {
			return Collections.EMPTY_LIST;
		}

		List<ClientSummary> clients = new ArrayList<>();

		for (IndividualDetail detail : INDIVIDUALS) {
			clients.add(indivToSummary(detail));
		}

		for (OrganisationDetail detail : ORGANISATIONS) {
			clients.add(orgToSummary(detail));
		}

		return clients;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CodeOption> retrieveCodes(final String table) throws ServiceException {
		List<CodeOption> options = new ArrayList<>();

		switch (table) {
			case "country":
				options.add(new CodeOption("A", "Australia"));
				options.add(new CodeOption("NZ", "New Zealand"));
				options.add(new CodeOption("UK", "United Kingdom"));
				break;
			case "currency":
				options.add(new CodeOption("AUD", "Australia Dollar"));
				options.add(new CodeOption("GBP", "British Pound"));
				options.add(new CodeOption("USD", "US Dollar"));
				break;
		}

		return options;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IndividualDetail retrieveIndividual(final String clientId) throws ServiceException, ClientNotFoundException {
		for (IndividualDetail detail : INDIVIDUALS) {
			if (Objects.equals(clientId, detail.getClientId())) {
				return detail;
			}
		}
		throw new ClientNotFoundException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createIndividual(final IndividualDetail detail) throws ServiceException {
		if ("error".equals(detail.getFirstName())) {
			throw new ServiceException("Mock error");
		}
		String id = UUID.randomUUID().toString();

		detail.setClientId(id);
		INDIVIDUALS.add(detail);
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateIndividual(final IndividualDetail detail) throws ServiceException {
		if ("error".equals(detail.getFirstName())) {
			throw new ServiceException("Mock error");
		}
		INDIVIDUALS.add(detail);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrganisationDetail retrieveOrganisation(final String clientId) throws ServiceException, ClientNotFoundException {
		for (OrganisationDetail detail : ORGANISATIONS) {
			if (Objects.equals(clientId, detail.getClientId())) {
				return detail;
			}
		}
		throw new ClientNotFoundException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String createOrganisation(final OrganisationDetail detail) throws ServiceException {
		if ("error".equals(detail.getName())) {
			throw new ServiceException("Mock error");
		}
		String id = UUID.randomUUID().toString();

		detail.setClientId(id);
		ORGANISATIONS.add(detail);
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateOrganisation(final OrganisationDetail detail) throws ServiceException {
		if ("error".equals(detail.getName())) {
			throw new ServiceException("Mock error");
		}
		ORGANISATIONS.add(detail);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigDecimal retrieveConversionRate(final String fromCode, final String toCode) {
		if (Objects.equals(fromCode, "USD")) {
			return BigDecimal.valueOf(1.2);
		} else if (Objects.equals(fromCode, "AUD")) {
			return BigDecimal.valueOf(0.7);
		}
		return BigDecimal.valueOf(1.6);
	}

	@Override
	public List<DocumentDetail> retrieveClientDocuments(final String clientId) throws ServiceException, ClientNotFoundException {
		// Build mock list of document details
		List<DocumentDetail> docs = new ArrayList<>(DOCUMENTS.values());
		return docs;
	}

	@Override
	public DocumentContent retrieveDocument(final String documentId) throws ServiceException, DocumentNotFoundException {

		DocumentDetail doc = DOCUMENTS.get(documentId);
		if (doc == null) {
			throw new DocumentNotFoundException();
		}

		// Sleep for 3 seconds
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			throw new ServiceException("Could not process thread. " + e.getMessage(), e);
		}

		DocumentContent content = new DocumentContent(documentId, getDocumentBytes(doc), doc.getResourcePath(), getDocumentMimeType(doc));
		return content;
	}

	/**
	 * @return the bytes from the resource
	 */
	private byte[] getDocumentBytes(final DocumentDetail doc) throws ServiceException {
		try (InputStream stream = getClass().getResourceAsStream(doc.getResourcePath())) {
			return IOUtils.toByteArray(stream);
		} catch (Exception e) {
			throw new ServiceException("Error loading resource." + e.getMessage(), e);
		}
	}

	private String getDocumentMimeType(final DocumentDetail doc) {
		String resource = doc.getResourcePath();
		// Just the MIME Types for the MOCK Data
		if (resource.endsWith("jpg")) {
			return "image/jpg";
		} else if (resource.endsWith("pdf")) {
			return "application/pdf";
		} else if (resource.endsWith("docx")) {
			return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		} else {
			return "";
		}
	}

}
