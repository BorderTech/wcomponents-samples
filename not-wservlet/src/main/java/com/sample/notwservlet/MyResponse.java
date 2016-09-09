package com.sample.notwservlet;

import com.github.bordertech.wcomponents.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Default implementation for {@link MyContainer}.
 *
 * @author Jonathan Austin
 */
public class MyResponse implements Response {

	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	private final PrintWriter writer;

	/**
	 * @param writer the print writer
	 */
	public MyResponse(final PrintWriter writer) {
		this.writer = writer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OutputStream getOutputStream() throws IOException {
		return outputStream;
	}

	/**
	 * Sets the redirect.
	 *
	 * @param sendRedirect the URL to redirect to.
	 */
	@Override
	public void sendRedirect(final String sendRedirect) {
		// Do something
	}

	/**
	 * Sets the content type.
	 *
	 * @param contentType the content type.
	 */
	@Override
	public void setContentType(final String contentType) {
		// Do something
	}

	/**
	 * Sets a header.
	 *
	 * @param name the header name.
	 * @param value the header value.
	 */
	@Override
	public void setHeader(final String name, final String value) {
		// Do something
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendError(final int code, final String description) throws IOException {
		// Do something
	}

}
