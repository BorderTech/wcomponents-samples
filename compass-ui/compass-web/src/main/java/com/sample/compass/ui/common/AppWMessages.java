package com.sample.compass.ui.common;

import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.WMessages;

/**
 * Panel that holds the message container. Allows the messages to be included in AJAX responses.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AppWMessages extends WMessages {

	/**
	 * Construct panel.
	 */
	public AppWMessages() {
		this(false);
	}

	/**
	 * @param persistent true if messages persistent.
	 */
	public AppWMessages(final boolean persistent) {
		super(persistent);
		setMargin(new Margin(0, 0, Constants.GAP_LARGE, 0));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isVisible() {
		// Always visible
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isHidden() {
		// Hide the panel when there are no messages so AJAX spinner is not seen
		return getSuccessMessages().isEmpty() && getInfoMessages().isEmpty() && getWarningMessages().isEmpty()
				&& getErrorMessages().isEmpty() && !getValidationErrors().hasErrors();
	}

}
