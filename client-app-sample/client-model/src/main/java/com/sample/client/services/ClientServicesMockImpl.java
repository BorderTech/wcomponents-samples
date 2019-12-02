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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.io.IOUtils;

/**
 * Mock Client Services.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@SuppressWarnings({"BED_HIERARCHICAL_EXCEPTION_DECLARATION", "PMB_POSSIBLE_MEMORY_BLOAT"})
public class ClientServicesMockImpl implements ClientServices {

	private static final AtomicInteger IND_IDS = new AtomicInteger(1);
	private static final AtomicInteger ORG_IDS = new AtomicInteger(1);

	private static final Map<String, IndividualDetail> INDIVIDUALS = new HashMap<>();
	private static final Map<String, OrganisationDetail> ORGANISATIONS = new HashMap<>();
	private static final Map<String, DocumentDetail> DOCUMENTS = createDocuments();
	private static final Map<String, List<CodeOption>> TABLES = createTables();

	static {
		for (int i = 1; i < 10; i++) {
			IndividualDetail client = createIndividual(IND_IDS.getAndIncrement());
			INDIVIDUALS.put(client.getClientId(), client);
		}

		for (int i = 1; i < 10; i++) {
			OrganisationDetail client = createOrganisation(ORG_IDS.getAndIncrement());
			ORGANISATIONS.put(client.getClientId(), client);
		}

	}

	private static Map<String, List<CodeOption>> createTables() {

		Map<String, List<CodeOption>> tables = new HashMap<>();

		// Country
		List<CodeOption> options = new ArrayList<>();
		options.add(new CodeOption("A", "Australia"));
		options.add(new CodeOption("NZ", "New Zealand"));
		options.add(new CodeOption("UK", "United Kingdom"));
		tables.put("country", options);

		// Currency
		options = new ArrayList<>();
		options.add(new CodeOption("AUD", "Australia Dollar"));
		options.add(new CodeOption("GBP", "British Pound"));
		options.add(new CodeOption("USD", "US Dollar"));
		tables.put("currency", options);

		return tables;
	}

	/**
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
	 * @param detail the detail
	 * @return the summary
	 */
	private static ClientSummary orgToSummary(final OrganisationDetail detail) {
		ClientSummary summary = new ClientSummary(detail.getClientId(), ClientType.ORGANISATION);
		if (detail.getAddress() != null) {
			summary.setAddress(detail.getAddress().toString());
		}
		summary.setName(detail.getName());
		return summary;
	}

	/**
	 * @param detail the detail
	 * @return the summary
	 */
	private static ClientSummary indivToSummary(final IndividualDetail detail) {
		ClientSummary summary = new ClientSummary(detail.getClientId(), ClientType.INDIVIDUAL);
		if (detail.getAddress() != null) {
			summary.setAddress(detail.getAddress().toString());
		}
		summary.setName(detail.getFirstName() + " " + detail.getLastName());
		if (detail.getIdentifications() != null) {
			summary.setIdentifications(new ArrayList<>(detail.getIdentifications()));
		}
		return summary;
	}

	/**
	 * @return the mock documents
	 */
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

	@Override
	public List<String> retrieveTables() throws ServiceException {
		return new ArrayList<>(TABLES.keySet());
	}

	@Override
	public List<CodeOption> retrieveCodes(final String table) throws ServiceException {
		List<CodeOption> options = TABLES.get(table);
		if (options == null) {
			throw new ServiceException("Table not found [" + table + "]");
		}
		return options;
	}

	@Override
	public List<ClientSummary> searchClients(final String search) throws ServiceException {

		if ("error".equals(search)) {
			throw new ServiceException("Mock error");
		}

		if ("none".equals(search)) {
			return Collections.EMPTY_LIST;
		}

		List<ClientSummary> clients = new ArrayList<>();

		for (IndividualDetail detail : searchIndividuals(search)) {
			clients.add(indivToSummary(detail));
		}

		for (OrganisationDetail detail : searchOrganisations(search)) {
			clients.add(orgToSummary(detail));
		}

		return clients;
	}

	@Override
	public List<IndividualDetail> searchIndividuals(final String search) throws ServiceException {

		if ("error".equals(search)) {
			throw new ServiceException("Mock error");
		}

		if ("none".equals(search)) {
			return Collections.EMPTY_LIST;
		}

		List<IndividualDetail> clients = new ArrayList<>();

		for (IndividualDetail detail : INDIVIDUALS.values()) {
			// Basic search
			if (isMatch(search, detail.getFirstName())
					|| isMatch(search, detail.getLastName())) {
				clients.add(detail);
			}
		}

		return clients;
	}

	@Override
	public IndividualDetail retrieveIndividual(final String clientId) throws ServiceException, ClientNotFoundException {
		IndividualDetail detail = INDIVIDUALS.get(clientId);
		if (detail == null) {
			throw new ClientNotFoundException();
		}
		return detail;
	}

	@Override
	public IndividualDetail createIndividual(final IndividualDetail detail) throws ServiceException {

		if ("error".equals(detail.getFirstName())) {
			throw new ServiceException("Mock error");
		}

		String id = "INDIV" + IND_IDS.getAndIncrement();
		detail.setClientId(id);
		INDIVIDUALS.put(id, detail);

		return detail;
	}

	@Override
	public IndividualDetail updateIndividual(final IndividualDetail detail) throws ServiceException {

		if ("error".equals(detail.getFirstName())) {
			throw new ServiceException("Mock error");
		}

		String key = detail.getClientId();
		// Check exists
		if (!INDIVIDUALS.containsKey(key)) {
			throw new ServiceException("Individual does not exist [" + key + "].");
		}
		// Add updated
		INDIVIDUALS.put(key, detail);
		return detail;
	}

	@Override
	public void deleteIndividual(final String clientId) throws ServiceException {
		// Check exists
		if (!INDIVIDUALS.containsKey(clientId)) {
			throw new ServiceException("Individual does not exist [" + clientId + "].");
		}
		// Remove
		INDIVIDUALS.remove(clientId);
	}

	@Override
	public List<OrganisationDetail> searchOrganisations(final String search) throws ServiceException {

		if ("error".equals(search)) {
			throw new ServiceException("Mock error");
		}

		if ("none".equals(search)) {
			return Collections.EMPTY_LIST;
		}

		List<OrganisationDetail> clients = new ArrayList<>();

		for (OrganisationDetail detail : ORGANISATIONS.values()) {
			// Basic search
			if (isMatch(search, detail.getName())) {
				clients.add(detail);
			}
		}

		return clients;
	}

	@Override
	public OrganisationDetail retrieveOrganisation(final String clientId) throws ServiceException, ClientNotFoundException {
		OrganisationDetail detail = ORGANISATIONS.get(clientId);
		if (detail == null) {
			throw new ClientNotFoundException();
		}
		return detail;
	}

	@Override
	public OrganisationDetail createOrganisation(final OrganisationDetail detail) throws ServiceException {
		if ("error".equals(detail.getName())) {
			throw new ServiceException("Mock error");
		}

		String id = "ORG" + ORG_IDS.getAndIncrement();
		detail.setClientId(id);

		ORGANISATIONS.put(id, detail);
		return detail;
	}

	@Override
	public OrganisationDetail updateOrganisation(final OrganisationDetail detail) throws ServiceException {
		if ("error".equals(detail.getName())) {
			throw new ServiceException("Mock error");
		}

		String key = detail.getClientId();
		if (!ORGANISATIONS.containsKey(key)) {
			throw new ServiceException("Organisation does not exist [" + key + "].");
		}
		// Update
		ORGANISATIONS.put(key, detail);
		return detail;
	}

	@Override
	public void deleteOrganisation(final String clientId) throws ServiceException {
		// Check exists
		if (!ORGANISATIONS.containsKey(clientId)) {
			throw new ServiceException("Organisation does not exist [" + clientId + "].");
		}
		// Remove
		ORGANISATIONS.remove(clientId);
	}

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

	private boolean isMatch(final String search, final String value) {
		if (search == null || search.isEmpty()) {
			return true;
		}
		return value.toLowerCase().contains(search.toLowerCase());
	}

}
