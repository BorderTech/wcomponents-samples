package com.sample.client.ui.view;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.SimpleBeanBoundTableModel;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WList;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WSubMenu;
import com.github.bordertech.wcomponents.WTable;
import com.github.bordertech.wcomponents.WTableColumn;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.validation.ValidatingAction;
import com.sample.client.model.ClientSummary;
import com.sample.client.model.IndividualDetail;
import com.sample.client.model.OrganisationDetail;
import com.sample.client.services.ClientServicesHelper;
import com.sample.client.ui.application.ClientApp;
import com.sample.client.ui.common.ClientWMessages;
import com.sample.client.ui.common.Constants;
import com.sample.client.ui.common.WTextCountryCodeDesc;
import com.sample.client.ui.common.WTextPassportNumber;
import com.sample.client.ui.util.ClientServicesHelperFactory;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Search view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class SearchView extends WSection implements MessageContainer {

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(SearchView.class);

	private static final ClientServicesHelper CLIENT_SERVICES = ClientServicesHelperFactory.getInstance();

	/**
	 * Main controller.
	 */
	private final ClientApp app;

	private final WTextField txtSearch = new WTextField();

	/**
	 * Messages for this view.
	 */
	private final WMessages messages = new ClientWMessages();

	private final WMenu menu = new WMenu();

	private final WPanel resultsPanel = new WPanel();

	private final WTable table = new WTable();

	/**
	 * @param app the client app.
	 */
	public SearchView(final ClientApp app) {
		super("Search");
		this.app = app;

		WPanel content = getContent();

		content.add(menu);
		setupMenu();

		// Messages
		content.add(messages);

		WFieldLayout layout = new WFieldLayout(WFieldLayout.LAYOUT_FLAT);
		layout.setLabelWidth(20);
		layout.setMargin(Constants.NORTH_MARGIN_LARGE);
		content.add(layout);

		layout.addField("Search criteria", txtSearch).setInputWidth(100);
		txtSearch.setMandatory(true);

		WButton searchButton = new WButton("Search");
		searchButton.setAction(new ValidatingAction(messages.getValidationErrors(), layout) {
			@Override
			public void executeOnValid(final ActionEvent event) {
				doSearch(txtSearch.getValue());
			}
		});
		layout.addField(searchButton);

		WAjaxControl ajax = new WAjaxControl(searchButton, new AjaxTarget[]{messages, resultsPanel});
		content.add(ajax);

		// Table
		content.add(resultsPanel);
		resultsPanel.setMargin(Constants.NORTH_MARGIN_LARGE);
		resultsPanel.add(table);
		setupTable();

		// Ids
		setIdName("srch");
		setNamingContext(true);

		content.setDefaultSubmitButton(searchButton);

	}

	private void setupMenu() {

		WSubMenu subMenu = new WSubMenu("Create");
		menu.add(subMenu);

		WMenuItem item = new WMenuItem("Individual");
		item.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				app.createIndividiual();
			}
		});
		subMenu.add(item);

		item = new WMenuItem("Organisation");
		item.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				app.createOrganisation();
			}
		});
		subMenu.add(item);

		// Reset
		item = new WMenuItem("Reset");
		item.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				// Reset to force call service again
				doReset();
			}
		});
		menu.add(item);

		// Currency
		item = new WMenuItem("Currency");
		item.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				app.showCurrency();
			}
		});
		menu.add(item);

		// Documents
		item = new WMenuItem("Documents");
		item.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				app.showDocuments();
			}
		});
		menu.add(item);

	}

	/**
	 * Setup the results table.
	 */
	private void setupTable() {
		table.setIdName("rs");

		// View client
		final WButton viewClient = new WButton("Display");
		viewClient.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				ClientSummary client = (ClientSummary) viewClient.getBeanValue();
				switch (client.getType()) {
					case Individual:
						doHandleIndividual(client.getClientId(), false);
						break;
					case Organisation:
						doHandleOrganisation(client.getClientId(), false);
						break;
				}
			}
		});
		viewClient.setImageUrl("icons/view.png");
		viewClient.setToolTip("view");

		// Update client
		final WButton updateClient = new WButton("Update");
		updateClient.setHtmlClass("updateButton");
		updateClient.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				ClientSummary client = (ClientSummary) viewClient.getBeanValue();
				switch (client.getType()) {
					case Individual:
						doHandleIndividual(client.getClientId(), true);
						break;
					case Organisation:
						doHandleOrganisation(client.getClientId(), true);
						break;
				}
			}
		});
		updateClient.setImageUrl("icons/edit.png");
		updateClient.setToolTip("update");
		final WContainer actionsContainer = new WContainer();
		actionsContainer.add(viewClient);
		actionsContainer.add(updateClient);

		WList listCountry = new WList(WList.Type.STACKED);
		listCountry.setRepeatedComponent(new WTextCountryCodeDesc());

		WList listPassport = new WList(WList.Type.STACKED);
		listPassport.setRepeatedComponent(new WTextPassportNumber());

		table.setMargin(Constants.SOUTH_MARGIN_LARGE);
		table.addColumn(new WTableColumn("Name", new WText()));
		table.addColumn(new WTableColumn("Address", new WText()));
		table.addColumn(new WTableColumn("Country", listCountry));
		table.addColumn(new WTableColumn("Identification", listPassport));
		table.addColumn(new WTableColumn("Actions", actionsContainer));
		table.setStripingType(WTable.StripingType.ROWS);
		table.setNoDataMessage("No clients found.");

		SimpleBeanBoundTableModel model = new SimpleBeanBoundTableModel(new String[]{"name", "address", "identifications", "identifications", "."});
		table.setTableModel(model);

		table.setBeanProperty(".");

		table.setVisible(false);
	}

	public void refreshClientSummary(final ClientSummary summary) {
		List<ClientSummary> clients = (List<ClientSummary>) table.getBean();
		int idx = clients.indexOf(summary);
		if (idx >= 0) {
			clients.set(idx, summary);
		}
	}

	/**
	 * Refresh results. Remove results from the cache.
	 */
	public void doReset() {
		reset();
	}

	private void doSearch(final String criteria) {
		try {
			table.reset();
			List<ClientSummary> clients = CLIENT_SERVICES.searchClients(criteria);
			if (clients.isEmpty()) {
				messages.info("No clients found");
				return;
			}
			table.setBean(clients);
			table.setVisible(true);
		} catch (Exception e) {
			String msg = "Error searching for clients. " + e.getMessage();
			LOG.error(msg, e);
			messages.error(msg);
		}

	}

	private void doHandleIndividual(final String clientId, final boolean update) {

		try {
			IndividualDetail detail = CLIENT_SERVICES.retrieveIndividual(clientId);
			app.viewIndividual(detail, update);
		} catch (Exception e) {
			String msg = "Error retrieving individual [" + clientId + "]. " + e.getMessage();
			LOG.error(msg, e);
			messages.error(msg);
		}

	}

	private void doHandleOrganisation(final String clientId, final boolean update) {
		try {
			OrganisationDetail detail = CLIENT_SERVICES.retrieveOrganisation(clientId);
			app.viewOrganisation(detail, update);
		} catch (Exception e) {
			String msg = "Error retrieving organisation [" + clientId + "]. " + e.getMessage();
			LOG.error(msg, e);
			messages.error(msg);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WMessages getMessages() {
		return messages;
	}

}
