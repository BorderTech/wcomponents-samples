package com.github.bordertech.wcomponents.task;

import com.github.bordertech.wcomponents.util.Factory;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Uses a cache to hold the future allowing the cache key reference to be serializable.
 *
 * @author Jonathan Austin
 * @param <T> the future get type
 * @since 1.0.0
 */
public class TaskFutureImpl<T> implements TaskFuture<T> {

	private static final FutureCache CACHE = Factory.newInstance(FutureCache.class);

	private final String id = UUID.randomUUID().toString();

	/**
	 * @param future the backing future
	 */
	public TaskFutureImpl(final Future<T> future) {
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
		CACHE.putFuture(id, future);
	}

	/**
	 * @return the future object from the cache
	 */
	protected final Future<T> getFuture() {
		return CACHE.getFuture(id);
	}

}
