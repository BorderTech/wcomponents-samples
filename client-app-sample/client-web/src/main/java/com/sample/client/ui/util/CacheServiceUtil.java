package com.sample.client.ui.util;

import com.github.bordertech.wcomponents.util.Config;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.apache.commons.configuration.Configuration;

/**
 * Cache Service Util.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class CacheServiceUtil {

	private static final String CACHE_NAME = "wc-client-model";

	private static final Configuration CONFIG = Config.getInstance();

	private static final Cache<String, Serializable> CACHE;

	static {

		// Setup Cache
		Cache<String, Serializable> cache = Caching.getCache(CACHE_NAME, String.class, Serializable.class);
		if (cache == null) {
			// Default time to live
			int timetolive = CONFIG.getInt("client.cache.timetolive.success", 300);
			final CacheManager mgr = Caching.getCachingProvider().getCacheManager();
			MutableConfiguration<String, Serializable> config = new MutableConfiguration<>();
			config.setTypes(String.class, Serializable.class);
			config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, timetolive)));
			cache = mgr.createCache(CACHE_NAME, config);
		}
		CACHE = cache;
	}

	/**
	 * Thread wait interval (in milli seconds).
	 */
	private static final int CACHE_WAIT_INTERVAL = CONFIG.getInt("client.cache.wait.interval", 300);

	/**
	 * Thread max wait intervals.
	 */
	private static final int CACHE_MAX_WAIT_INTERVALS = CONFIG.getInt("client.cache.wait.max.intervals", 200);

	/**
	 * Processing flag used in cache.
	 */
	private static final String PROCESSING = UUID.randomUUID().toString();

	public static Serializable getResult(final String key) {
		Serializable result = CACHE.get(key);
		return Objects.equals(PROCESSING, result) ? null : result;
	}

	public static void setResult(final String key, final Serializable result) {
		CACHE.put(key, result);
	}

	public static void clearResult(final String key) {
		CACHE.remove(key);
	}

	public static void startProcessing(final String key) {
		CACHE.put(key, PROCESSING);
	}

	public static boolean isProcessing(final String key) {
		Serializable result = CACHE.get(key);
		return result != null && Objects.equals(PROCESSING, result);
	}

	/**
	 * Wait till there is a response or null in the CACHE.
	 *
	 * @param key the cache key to wait for
	 * @return cache entry or null
	 */
	public static Serializable getResultWait(final String key) {
		int counts = 0;
		while (true) {
			Serializable result = CACHE.get(key);
			if (result == null) {
				// Key not in cache
				return null;
			} else if (!Objects.equals(PROCESSING, result)) {
				// Actual result
				return result;
			}
			// Still processing
			if (counts++ > CACHE_MAX_WAIT_INTERVALS) {
				return null;
			}
			threadSleep();
		}
	}

	public static void threadSleep() {
		try {
			Thread.currentThread().sleep(CACHE_WAIT_INTERVAL);
		} catch (InterruptedException e) {
			throw new IllegalStateException("Error waiting for cache. " + e.getMessage(), e);
		}

	}

}
