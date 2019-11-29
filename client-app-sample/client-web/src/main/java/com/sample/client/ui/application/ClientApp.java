package com.sample.client.ui.application;

import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WCardManager;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTimeoutWarning;
import com.github.bordertech.wcomponents.WebUtilities;
import com.sample.client.model.AddressDetail;
import com.sample.client.model.ClientSummary;
import com.sample.client.model.IndividualDetail;
import com.sample.client.model.OrganisationDetail;
import com.sample.client.ui.common.ClientWMessages;
import com.sample.client.ui.common.Constants;
import com.sample.client.ui.common.ViewMode;
import com.sample.client.ui.view.CurrencyView;
import com.sample.client.ui.view.DocumentView;
import com.sample.client.ui.view.IndividualView;
import com.sample.client.ui.view.OrganisationView;
import com.sample.client.ui.view.SearchView;
import java.util.Date;

/**
 * The root WComponent application for the Sample web UI.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ClientApp extends WApplication implements MessageContainer {

	/**
	 * Messages.
	 */
	private final WMessages messages = new ClientWMessages();

	/**
	 * Card manager.
	 */
	private final WCardManager mgr = new WCardManager();
	private final SearchView vwSearch;
	private final IndividualView vwIndividual;
	private final OrganisationView vwOrganisation;
	private final CurrencyView vwCurrency;
	private final DocumentView vwDocuments;

	/**
	 * Creates a new Sample App.
	 */
	public ClientApp() {

		// Custom css
		addCssFile("/css/app.css");

		// Header
		final WPanel header = new WPanel(WPanel.Type.HEADER);
		add(header);
		header.add(new WHeading(HeadingLevel.H1, "Client App"));

		// Detail
		WPanel detail = new WPanel();
		detail.setMargin(new Margin(Constants.GAP_LARGE));
		add(detail);

		// Messages
		detail.add(messages);

		// Card manager
		detail.add(mgr);

		// Cards
		vwSearch = new SearchView(this);
		vwIndividual = new IndividualView(this);
		vwOrganisation = new OrganisationView(this);
		vwCurrency = new CurrencyView(this);
		vwDocuments = new DocumentView(this);
		mgr.add(vwSearch);
		mgr.add(vwIndividual);
		mgr.add(vwOrganisation);
		mgr.add(vwCurrency);
		mgr.add(vwDocuments);

		// Footer
		final WPanel footer = new WPanel(WPanel.Type.FOOTER);
		add(footer);

		footer.add(new WText(new Date().toString()));

		add(new WTimeoutWarning());

		// IDs
		header.setIdName("hdr");
		messages.setIdName("msgs");
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		// Just for a PMD error
		super.preparePaintComponent(request);
	}

	/**
	 * Show currency view.
	 */
	public void showCurrency() {
		vwCurrency.reset();
		mgr.makeVisible(vwCurrency);
	}

	/**
	 * Show documents view.
	 */
	public void showDocuments() {
		vwDocuments.reset();
		mgr.makeVisible(vwDocuments);
	}

	/**
	 * Show search view.
	 */
	public void showSearch() {
		mgr.makeVisible(vwSearch);
	}

	/**
	 * Show search view and apply updates.
	 *
	 * @param summary the updated client details
	 */
	public void showSearchWithUpdate(final ClientSummary summary) {
		vwSearch.refreshClientSummary(summary);
		showSearch();
	}

	/**
	 * Show organisation view.
	 *
	 * @param detail the organisation details
	 * @param update true if allow update
	 */
	public void viewOrganisation(final OrganisationDetail detail, final boolean update) {
		vwOrganisation.reset();
		vwOrganisation.setViewMode(update ? ViewMode.UPDATE : ViewMode.READONLY);
		vwOrganisation.setDetail(detail);
		mgr.makeVisible(vwOrganisation);
	}

	/**
	 * Show create organisation view.
	 */
	public void createOrganisation() {
		OrganisationDetail detail = new OrganisationDetail();
		detail.setAddress(new AddressDetail());
		vwOrganisation.reset();
		vwOrganisation.setViewMode(ViewMode.CREATE);
		vwOrganisation.setDetail(detail);
		mgr.makeVisible(vwOrganisation);
	}

	/**
	 * Show individual view.
	 *
	 * @param detail the individual details
	 * @param update true if allow update
	 */
	public void viewIndividual(final IndividualDetail detail, final boolean update) {
		vwIndividual.reset();
		vwIndividual.setViewMode(update ? ViewMode.UPDATE : ViewMode.READONLY);
		vwIndividual.setDetail(detail);
		mgr.makeVisible(vwIndividual);
	}

	/**
	 * Show create individual view.
	 */
	public void createIndividiual() {
		IndividualDetail detail = new IndividualDetail();
		detail.setAddress(new AddressDetail());
		vwIndividual.reset();
		vwIndividual.setViewMode(ViewMode.CREATE);
		vwIndividual.setDetail(detail);
		mgr.makeVisible(vwIndividual);
	}

	@Override
	public String getTitle() {
		return "Client App - " + getCurrentTitle();
	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

	@Override
	public void handleStepError() {
		super.handleStepError();
		getMessages()
				.warn("A request was made that is not in the expected sequence and the application has been refreshed to its current state.");
	}

	/**
	 * @return the title of the current card
	 */
	private String getCurrentTitle() {
		return ((WSection) mgr.getVisible()).getDecoratedLabel().getText();
	}

	/**
	 * Retrieves the BrisApp ancestor of the given component.
	 *
	 * @param descendant the component to find the BrisApp ancestor of.
	 * @return the BrisApp ancestor for the given component, or null if not found.
	 */
	public static ClientApp getInstance(final WComponent descendant) {
		return WebUtilities.getAncestorOfClass(ClientApp.class, descendant);
	}

}
