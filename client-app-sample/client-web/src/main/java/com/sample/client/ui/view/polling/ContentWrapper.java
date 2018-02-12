package com.sample.client.ui.view.polling;

import com.github.bordertech.taskmaster.service.ResultHolder;
import com.github.bordertech.wcomponents.ContentAccess;
import com.sample.client.model.DocumentContent;

/**
 * Wrap the document content.
 *
 * @author jonathan
 */
public abstract class ContentWrapper implements ContentAccess {

	private final String fileName;
	private final String mimeType;

	/**
	 * @param fileName the file name
	 * @param mimeType the mime type
	 */
	public ContentWrapper(final String fileName, final String mimeType) {
		this.fileName = fileName;
		this.mimeType = mimeType;
	}

	@Override
	public byte[] getBytes() {
		return getDocument().getResult().getBytes();
	}

	@Override
	public String getDescription() {
		return fileName;
	}

	@Override
	public String getMimeType() {
		return mimeType;
	}

	/**
	 *
	 * @return the service result (preferably from a cache)
	 */
	protected abstract ResultHolder<?, DocumentContent> getDocument();

}
