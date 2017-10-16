package com.github.bordertech.wcomponents.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Handle running tasks via {@link ExecutorService}.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class TaskManagerExecutorServiceImpl implements TaskManager {

	private static final ExecutorService POOL = Executors.newCachedThreadPool();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void shutdown() {
		POOL.shutdown();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <T> TaskFuture<T> submit(final Runnable task, final T result) {
		Future<T> future = POOL.submit(task, result);
		return new TaskFutureImpl<>(future);
	}

}
