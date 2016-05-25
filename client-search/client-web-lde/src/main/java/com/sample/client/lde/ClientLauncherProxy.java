package com.sample.client.lde;

import com.github.bordertech.wcomponents.lde.PlainLauncher;

/**
 * Proxy to the LDE Launcher to allow IDEs to find a main class.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ClientLauncherProxy {

	/**
	 * An instance of the launcher.
	 */
	private static PlainLauncher launcher;

	/**
	 * The entry point when the launcher is run as a java application.
	 *
	 * @param args command-line arguments, ignored.
	 * @throws Exception on error
	 */
	public static void main(final String[] args) throws Exception {
		launcher = new PlainLauncher();
		launcher.run();
	}

	/**
	 * Stop the launcher.
	 *
	 * @throws InterruptedException on error
	 */
	public static void stopLauncher() throws InterruptedException {
		if (launcher != null) {
			launcher.stop();
		}
	}

}
