package com.sample.compass.ui.view;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WText;
import com.sample.compass.services.CompassServicesHelper;
import com.sample.compass.ui.application.CompassApp;
import com.sample.compass.ui.common.AppWMessages;
import com.sample.compass.ui.common.Constants;
import com.sample.compass.ui.util.CompassServicesHelperFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Conversion view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class CurrencyView extends WSection implements MessageContainer {

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(CurrencyView.class);

	private static final CompassServicesHelper COMPASS_SERVICES = CompassServicesHelperFactory.getInstance();

	/**
	 * Messages for this view.
	 */
	private final WMessages messages = new AppWMessages();

	/**
	 * @param app the compass app.
	 */
	public CurrencyView(final CompassApp app) {
		super("Search");

		WPanel content = getContent();

		content.add(new WText("Hello"));

		// Menu
		WMenu menu = new WMenu();
		content.add(menu);

		WMenuItem item = new WMenuItem("Back");
		item.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				app.showSearch();
			}
		});
		menu.add(item);

		item = new WMenuItem("Reset");
		item.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doReset();
			}
		});
		menu.add(item);

		// Messages
		content.add(messages);

		WFieldLayout layout = new WFieldLayout(WFieldLayout.LAYOUT_FLAT);
		layout.setLabelWidth(20);
		layout.setMargin(Constants.NORTH_MARGIN_LARGE);
		content.add(layout);

		// Ids
		setIdName("cx");
		setNamingContext(true);
	}

	/**
	 * Refresh results. Remove results from the cache.
	 */
	public void doReset() {
		reset();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WMessages getMessages() {
		return messages;
	}

}
