package com.github.bordertech.wcomponents.polling;

/**
 * The interface used by the pollable component to call the service.
 *
 * @param <S> the criteria type
 * @param <T> the response type
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ServiceAction<S, T> {

	T service(final S criteria) throws PollingException;

}
