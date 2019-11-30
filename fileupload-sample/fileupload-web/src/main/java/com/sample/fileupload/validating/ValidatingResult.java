package com.sample.fileupload.validating;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hold validating result.
 */
public class ValidatingResult implements Serializable {

	private List<String> messages;

	/**
	 * @param message add error message
	 */
	public void addMessage(final String message) {
		if (messages == null) {
			messages = new ArrayList<>();
		}
		messages.add(message);
	}

	/**
	 * @return all error messages
	 */
	public List<String> getMessages() {
		if (messages == null) {
			return Collections.EMPTY_LIST;
		} else {
			return messages;
		}
	}
}
