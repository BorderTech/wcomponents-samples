package com.github.bordertech.wcomponents.task;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ContextListener to shutdown the task manager (release threads).
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class TaskContextListener implements ServletContextListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(final ServletContextEvent servletContextEvent) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(final ServletContextEvent servletContextEvent) {
		// Shutdown the task manager
		TaskManagerFactory.getInstance().shutdown();
	}
}
