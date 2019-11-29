package com.sample.client.ui.view;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WNumberField;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.sample.client.model.CodeOption;
import com.sample.client.services.ClientServicesHelper;
import com.sample.client.ui.application.ClientApp;
import com.sample.client.ui.common.ClientWMessages;
import com.sample.client.ui.common.Constants;
import com.sample.client.ui.util.ClientServicesHelperFactory;
import java.math.BigDecimal;
import java.util.Objects;
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

	private static final ClientServicesHelper CLIENT_SERVICES = ClientServicesHelperFactory.getInstance();

	private final WNumberField fromAmount = new WNumberField();

	private final WDropdown fromCurrency = new WDropdown(Constants.CURRENCY_TABLE_WITH_NULL);

	private final WDropdown toCurrency = new WDropdown(Constants.CURRENCY_TABLE_WITH_NULL);

	private final WNumberField convRate = new WNumberField();

	private final WNumberField convAmount = new WNumberField();

	/**
	 * Messages for this view.
	 */
	private final WMessages messages = new ClientWMessages();

	/**
	 * @param app the client app.
	 */
	public CurrencyView(final ClientApp app) {
		super("Currency");

		WPanel content = getContent();

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

		// From amount
		fromAmount.setDecimalPlaces(2);
		fromAmount.setMinValue(0.01);
		layout.addField("Amount", fromAmount);

		// From currency
		layout.addField("From currency", fromCurrency);

		// To currency
		layout.addField("To currency", toCurrency);

		// Conv rate
		convRate.setReadOnly(true);
		convRate.setDecimalPlaces(2);
		layout.addField("Conversion rate", convRate);

		// Conv amount
		convAmount.setReadOnly(true);
		convAmount.setDecimalPlaces(2);
		layout.addField("Converted amount", convAmount);

		// AJAX
		AjaxTarget[] targets = new AjaxTarget[]{messages, convRate, convAmount};
		content.add(new WAjaxControl(fromAmount, targets));
		content.add(new WAjaxControl(fromCurrency, targets));
		content.add(new WAjaxControl(toCurrency, targets));

		// Actions
		fromCurrency.setActionOnChange(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doCallCurrencyRate();
			}
		});
		toCurrency.setActionOnChange(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doCallCurrencyRate();
			}
		});

		// Ids
		setIdName("cx");
		setNamingContext(true);
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		doCalc();
	}

	/**
	 * Refresh results. Remove results from the cache.
	 */
	public void doReset() {
		reset();
	}

	private void doCalc() {
		if (fromAmount.getValue() == null || convRate.getValue() == null) {
			convAmount.reset();
		} else {
			BigDecimal amt = fromAmount.getValue().multiply(convRate.getValue());
			convAmount.setNumber(amt);
		}
	}

	private void doCallCurrencyRate() {

		if (fromCurrency.getSelected() == null || toCurrency.getSelected() == null) {
			convRate.reset();
			return;
		}

		String fromCode = ((CodeOption) fromCurrency.getSelected()).getCode();
		String toCode = ((CodeOption) toCurrency.getSelected()).getCode();

		if (Objects.equals(fromCode, toCode)) {
			convRate.setNumber(BigDecimal.ONE);
			return;
		}

		try {
			BigDecimal rate = CLIENT_SERVICES.retrieveConversionRate(fromCode, toCode);
			convRate.setNumber(rate);
		} catch (Exception e) {
			String msg = "Error retrieving conversion rate [" + fromCode + " to " + toCode + "]. " + e.getMessage();
			LOG.error(msg, e);
			messages.error(msg);
			convRate.reset();
		}
	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

}
