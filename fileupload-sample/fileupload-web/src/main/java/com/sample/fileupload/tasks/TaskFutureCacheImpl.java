package com.sample.fileupload.tasks;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;

/**
 * Uses a cache to hold the future allowing the cache key reference to be serializable.
 *
 * @author Jonathan Austin
 * @param <T> the future get type
 * @since 1.0.0
 */
public class TaskFutureCacheImpl<T> implements TaskFuture<T> {

	private static final String CACHE_NAME = "wc-future-task";

	private final String id = UUID.randomUUID().toString();

	/**
	 * @param future the backing future
	 */
	public TaskFutureCacheImpl(final Future<T> future) {
		setFuture(future);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean cancel(final boolean mayInterruptIfRunning) {
		return getFuture().cancel(mayInterruptIfRunning);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCancelled() {
		return getFuture().isCancelled();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isDone() {
		return getFuture().isDone();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get() throws InterruptedException, ExecutionException {
		return getFuture().get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return getFuture().get(timeout, unit);
	}

	/**
	 * @param future the future to save in the cache
	 */
	protected final void setFuture(final Future<T> future) {
		getCache().put(id, future);
	}

	/**
	 * @return the future object from the cache
	 */
	protected final Future<T> getFuture() {
		return getCache().get(id);
	}

	/**
	 * Use a cache to hold a reference to the future so the user context can be serialized. Future Objects are not
	 * serializable.
	 *
	 * @return the cache instance
	 */
	protected synchronized Cache<String, Future> getCache() {
		Cache<String, Future> cache = Caching.getCache(CACHE_NAME, String.class, Future.class);
		if (cache == null) {
			final CacheManager mgr = Caching.getCachingProvider().getCacheManager();
			MutableConfiguration<String, Future> config = new MutableConfiguration<>();
			config.setTypes(String.class, Future.class);
			config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, 5)));
			// No need to serialize the result (Future is not serializable)
			config.setStoreByValue(false);
			cache = mgr.createCache(CACHE_NAME, config);
		}
		return cache;
	}

}
