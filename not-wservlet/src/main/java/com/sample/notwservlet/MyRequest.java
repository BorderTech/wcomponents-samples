package com.sample.notwservlet;

import com.github.bordertech.wcomponents.AbstractRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 * Default implementation for {@link MyContainer}.
 *
 * @author Jonathan Austin
 */
public class MyRequest extends AbstractRequest {

	private final Map<String, String[]> parameters = new HashMap<>(0);

	private final Map<String, FileItem[]> files = new HashMap<>(0);

	private final Map<String, Serializable> attributes = new HashMap<>();

	private final Map<String, Serializable> sessionAttributes;

	private final String method;

	/**
	 * @param sessionAttributes the session attributes
	 * @param method the request method (eg POST or GET)
	 */
	public MyRequest(final Map<String, Serializable> sessionAttributes, final String method) {
		this.sessionAttributes = sessionAttributes;
		this.method = method;
	}

	/**
	 * @return the parameter map.
	 */
	@Override
	public Map<String, String[]> getParameters() {
		return parameters;
	}

	/**
	 * @return the file upload map.
	 */
	@Override
	public Map<String, FileItem[]> getFiles() {
		return files;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Serializable getAttribute(final String key) {
		return attributes.get(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setAttribute(final String key, final Serializable value) {
		attributes.put(key, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Serializable getSessionAttribute(final String key) {
		return sessionAttributes.get(key);
	}

	/**
	 * Note that this mock request just maps to the global session.
	 *
	 * @param key the attribute key
	 * @return the value of the session attribute with the given key
	 */
	@Override
	public Serializable getAppSessionAttribute(final String key) {
		return getSessionAttribute(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSessionAttribute(final String key, final Serializable value) {
		sessionAttributes.put(key, value);
	}

	/**
	 * Note that this mock request just maps to the global session.
	 *
	 * @param key the session attribute key
	 * @param value the value of the session attribute
	 */
	@Override
	public void setAppSessionAttribute(final String key, final Serializable value) {
		setSessionAttribute(key, value);
	}

	/**
	 * Support for Public Render Parameters in Portal. In a Servlet environment, this will be the same as the session.
	 *
	 * @param key The key for the parameter.
	 * @param value The value of the parameter.
	 * @since 1.0.0
	 * @deprecated portal specific. user {@link #setSessionAttribute(String, Serializable)}
	 */
	@Override
	public void setRenderParameter(final String key, final Serializable value) {
		setSessionAttribute(key, value);
	}

	/**
	 * Support for Public Render Parameters in Portal. In a Servlet environment, this will be the same as the session.
	 *
	 * @param key The key for the parameter.
	 * @return The value of the parameter.
	 * @since 1.0.0
	 * @deprecated portal specific. user {@link #getSessionAttribute(String)}
	 */
	@Override
	public Serializable getRenderParameter(final String key) {
		return getSessionAttribute(key);
	}

	/**
	 * Returns true if the user is in the given role.
	 *
	 * @param role a String specifying the name of the role.
	 * @return true if the user is in the given role, otherwise false.
	 */
	@Override
	public boolean isUserInRole(final String role) {
		return true;
	}

	/**
	 * @return the name of the HTTP method with which this request was made, for example, GET, POST, or PUT.
	 */
	@Override
	public String getMethod() {
		return method;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxInactiveInterval() {
		return 300;
	}

}
