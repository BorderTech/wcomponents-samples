package com.sample.client.ui.common;

import com.github.bordertech.wcomponents.Margin;
import com.sample.client.ui.util.LookupTableImpl;

/**
 * Holds the constants used by the UI.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public final class Constants {

	/**
	 * Prevent instantiation of this utility class.
	 */
	private Constants() {
		// Do nothing
	}

	/**
	 * Country table details.
	 */
	public static final LookupTableImpl.TableDetails COUNTRY_TABLE = new LookupTableImpl.TableDetails("country");

	/**
	 * Country table with a null option details.
	 */
	public static final LookupTableImpl.TableDetails COUNTRY_TABLE_WITH_NULL = new LookupTableImpl.TableDetails("country", true);

	/**
	 * Currency table with a null option details.
	 */
	public static final LookupTableImpl.TableDetails CURRENCY_TABLE_WITH_NULL = new LookupTableImpl.TableDetails("currency", true);

	/**
	 * Default GAP.
	 */
	public static final int GAP = 6;

	/**
	 * Default Large GAP.
	 */
	public static final int GAP_LARGE = 12;

	/**
	 * North margin.
	 */
	public static final Margin NORTH_MARGIN = new Margin(6, 0, 0, 0);

	/**
	 * South margin.
	 */
	public static final Margin SOUTH_MARGIN = new Margin(0, 0, 6, 0);

	/**
	 * North South margin.
	 */
	public static final Margin NORTH_SOUTH_MARGIN = new Margin(6, 0, 6, 0);

	/**
	 * North margin large.
	 */
	public static final Margin NORTH_MARGIN_LARGE = new Margin(12, 0, 0, 0);

	/**
	 * South margin large.
	 */
	public static final Margin SOUTH_MARGIN_LARGE = new Margin(0, 0, 12, 0);

	/**
	 * North South margin large.
	 */
	public static final Margin NORTH_SOUTH_MARGIN_LARGE = new Margin(12, 0, 12, 0);

}
