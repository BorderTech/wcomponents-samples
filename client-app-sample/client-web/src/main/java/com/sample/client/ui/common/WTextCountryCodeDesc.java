package com.sample.client.ui.common;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.util.Factory;
import com.github.bordertech.wcomponents.util.LookupTable;
import com.sample.client.model.CodeOption;
import java.util.List;
import java.util.Objects;

/**
 * Display the country code description from the lookup table.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class WTextCountryCodeDesc extends WText {

	/**
	 * The Application-wide lookup-table to use.
	 */
	private static final LookupTable LOOKUP_TABLE = Factory.newInstance(LookupTable.class);

	/**
	 * Construct country code.
	 */
	public WTextCountryCodeDesc() {
		setBeanProperty("countryCode");
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			setInitialised(true);
			String code = (String) getBeanValue();
			if (code != null) {
				setText(getDesc(code));
			}
		}
	}

	/**
	 * @param code the country code
	 * @return the country description
	 */
	protected String getDesc(final String code) {
		List<CodeOption> options = (List<CodeOption>) LOOKUP_TABLE.getTable(Constants.COUNTRY_TABLE);
		String desc = null;
		if (options != null) {
			for (CodeOption option : options) {
				if (Objects.equals(option.getCode(), code)) {
					desc = option.getDescription();
					break;
				}
			}
		}
		return desc == null ? code : desc;
	}

}
