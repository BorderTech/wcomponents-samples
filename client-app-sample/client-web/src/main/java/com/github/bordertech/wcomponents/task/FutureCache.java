package com.github.bordertech.wcomponents.task;

import java.util.concurrent.Future;

/**
 * Provide a caching mechanism for the future.
 * <p>
 * As a future cannot be serialized the caching mechanism must allow for objects that cannot be serialized.
 * </p>
 *
 * @param <T> the future get result type
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface FutureCache<T> {

	/**
	 * @param key the key to the future
	 * @return the future or null
	 */
	Future<T> getFuture(final String key);

	/**
	 * @param key the key of the future to remove
	 */
	void removeFuture(final String key);

	/**
	 *
	 * @param key the key to the future
	 * @param future the future to cache
	 */
	void putFuture(final String key, final Future<T> future);

}
