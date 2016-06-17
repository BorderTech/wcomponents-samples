package com.sample.compass.ui.application;

import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.MessageContainer;
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
import com.sample.compass.ui.common.AppWMessages;
import com.sample.compass.ui.common.Constants;
import com.sample.compass.ui.view.CurrencyView;
import com.sample.compass.ui.view.SearchView;
import java.util.Date;

/**
 * The root WComponent application for the Sample web UI.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class CompassApp extends WApplication implements MessageContainer {

	/**
	 * Messages.
	 */
	private final WMessages messages = new AppWMessages();

	/**
	 * Card manager.
	 */
	private final WCardManager mgr = new WCardManager();
	private final SearchView vwSearch;
	private final CurrencyView vwCurrency;

	/**
	 * Creates a new Sample App.
	 */
	public CompassApp() {

		// Custom css
		addCssFile("/css/app.css");

		// Header
		final WPanel header = new WPanel(WPanel.Type.HEADER);
		add(header);
		header.add(new WHeading(HeadingLevel.H1, "Compass App"));

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
		vwCurrency = new CurrencyView(this);
		mgr.add(vwSearch);
		mgr.add(vwCurrency);

		// Footer
		final WPanel footer = new WPanel(WPanel.Type.FOOTER);
		add(footer);

		footer.add(new WText(new Date().toString()));

		add(new WTimeoutWarning());

		// IDs
		header.setIdName("hdr");
		messages.setIdName("msgs");
	}

	public void showCurrency() {
		vwCurrency.reset();
		mgr.makeVisible(vwCurrency);
	}

	public void showSearch() {
		mgr.makeVisible(vwSearch);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return "Compass App - " + getCurrentTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WMessages getMessages() {
		return messages;
	}

	/**
	 * {@inheritDoc}
	 */
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
	public static CompassApp getInstance(final WComponent descendant) {
		return WebUtilities.getAncestorOfClass(CompassApp.class, descendant);
	}

}
