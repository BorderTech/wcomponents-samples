package com.sample.compass.ui.view;

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
import com.github.bordertech.wcomponents.WTable;
import com.github.bordertech.wcomponents.WTableColumn;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WTextField;
import com.github.bordertech.wcomponents.validation.ValidatingAction;
import com.sample.compass.services.CompassServicesHelper;
import com.sample.compass.ui.application.CompassApp;
import com.sample.compass.ui.common.AppWMessages;
import com.sample.compass.ui.common.Constants;
import com.sample.compass.ui.util.CompassServicesHelperFactory;
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

	private static final CompassServicesHelper COMPASS_SERVICES = CompassServicesHelperFactory.getInstance();

	/**
	 * Main controller.
	 */
	private final CompassApp app;

	private final WTextField txtSearch = new WTextField();

	/**
	 * Messages for this view.
	 */
	private final WMessages messages = new AppWMessages();

	private final WMenu menu = new WMenu();

	private final WPanel resultsPanel = new WPanel();

	private final WTable table = new WTable();

	/**
	 * @param app the compass app.
	 */
	public SearchView(final CompassApp app) {
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

		// Reset
		WMenuItem item = new WMenuItem("Reset");
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

	}

	/**
	 * Setup the results table.
	 */
	private void setupTable() {
		table.setIdName("rs");

		// View client
		final WButton viewClient = new WButton("Display");

		// Update client
		final WButton updateClient = new WButton("Update");
		updateClient.setHtmlClass("updateButton");

		final WContainer actionsContainer = new WContainer();
		actionsContainer.add(viewClient);
		actionsContainer.add(updateClient);

		WList listCountry = new WList(WList.Type.STACKED);
		listCountry.setRepeatedComponent(new WText());

		WList listPassport = new WList(WList.Type.STACKED);
		listPassport.setRepeatedComponent(new WText());

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

	/**
	 * Refresh results. Remove results from the cache.
	 */
	public void doReset() {
		reset();
	}

	private void doSearch(final String criteria) {
		try {
			table.reset();
//			List<ClientSummary> clients = CLIENT_SERVICES.searchClients(criteria);
//			if (clients.isEmpty()) {
//				messages.info("No clients found");
//				return;
//			}
//			table.setBean(clients);
			table.setVisible(true);
		} catch (Exception e) {
			String msg = "Error searching for clients. " + e.getMessage();
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
