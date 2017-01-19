package com.sample.fileupload.tasks;

import com.github.bordertech.wcomponents.util.Factory;

/**
 * Return the TaskManager.
 *
 * @author jonathan
 * @since 1.0.0
 */
public final class TaskManagerFactory {

	private static final TaskManager INSTANCE = Factory.newInstance(TaskManager.class);

	/**
	 * Prevent instantiation.
	 */
	private TaskManagerFactory() {
	}

	/**
	 *
	 * @return the TaskManager instance
	 */
	public static TaskManager getInstance() {
		return INSTANCE;
	}
}
