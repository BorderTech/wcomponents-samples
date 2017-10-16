package com.github.bordertech.wcomponents.task;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
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
public class FutureCacheImpl<T> implements FutureCache<T> {

	private static final String CACHE_NAME = "wc-future-task";

	@Override
	public Future<T> getFuture(final String key) {
		return getCache().get(key);
	}

	@Override
	public void removeFuture(final String key) {
		getCache().remove(key);
	}

	@Override
	public void putFuture(String key, Future<T> future) {
		getCache().put(key, future);
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
