package com.sample.client.ui.common;

import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTextField;
import com.sample.client.model.CodeOption;
import com.sample.client.model.StateType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AddressPanel extends WPanel {

	public AddressPanel() {

		WFieldLayout layout = new WFieldLayout(WFieldLayout.LAYOUT_FLAT);
		layout.setLabelWidth(20);
		add(layout);

		// Street
		WTextField txtStreet = new WTextField();
		txtStreet.setMandatory(true);
		txtStreet.setBeanProperty("street");
		layout.addField("Address", txtStreet);

		// Suburb
		WTextField txtSuburb = new WTextField();
		txtSuburb.setMandatory(true);
		txtSuburb.setBeanProperty("suburb");
		layout.addField("Suburb", txtSuburb);

		// State
		WDropdown drpState = new WDropdown();
		drpState.setMandatory(true);
		drpState.setBeanProperty("state");
		layout.addField("State", drpState);

		List<StateType> options = new ArrayList<>(Arrays.asList(StateType.values()));
		options.add(0, null);
		drpState.setOptions(options);

		// Postcode
		WTextField txtPostcode = new WTextField();
		txtPostcode.setMandatory(true);
		txtPostcode.setBeanProperty("postcode");
		layout.addField("Postcode", txtPostcode);

		// Country
		WDropdown drpCountry = new WDropdown(Constants.COUNTRY_TABLE_WITH_NULL) {

			@Override
			protected void doUpdateBeanValue(final Object value) {
				if (value instanceof CodeOption) {
					super.doUpdateBeanValue(((CodeOption) value).getCode());
				} else {
					super.doUpdateBeanValue(value);
				}
			}

			@Override
			protected Object doHandleInvalidOption(final Object invalidOption) {
				WMessages.getInstance(this).info("Address country [" + invalidOption.toString() + "] is no longer valid.");
				return null;
			}
		};
		drpCountry.setMandatory(true);
		drpCountry.setBeanProperty("countryCode");
		layout.addField("Country", drpCountry);
	}

}
