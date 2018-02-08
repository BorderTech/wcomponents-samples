package com.sample.client.ui.view.polling;

import com.github.bordertech.wcomponents.ContentAccess;
import com.sample.client.model.DocumentContent;

/**
 *
 * @author jonathan
 */
public class ContentWrapper implements ContentAccess {

	private final DocumentContent content;

	public ContentWrapper(final DocumentContent content) {
		this.content = content;
	}

	@Override
	public byte[] getBytes() {
		return content.getBytes();
	}

	@Override
	public String getDescription() {
		return content.getFilename();
	}

	@Override
	public String getMimeType() {
		return content.getMimeType();
	}

}
