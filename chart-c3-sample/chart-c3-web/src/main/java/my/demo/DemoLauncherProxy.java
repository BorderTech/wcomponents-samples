package my.demo;

import com.github.bordertech.wcomponents.lde.PlainLauncher;

/**
 * Proxy to the LDE Launcher to allow IDEs to find a main class.
 *
 * @author Jonathan Austin
 */
public class DemoLauncherProxy {

	/**
	 * An instance of the launcher.
	 */
	private static PlainLauncher LAUNCHER;

	/**
	 * The entry point when the launcher is run as a java application.
	 *
	 * @param args command-line arguments, ignored.
	 * @throws Exception on error
	 */
	public static void main(final String[] args) throws Exception {
		LAUNCHER = new PlainLauncher();
		LAUNCHER.run();
	}

	/**
	 * Stop the launcher.
	 *
	 * @throws InterruptedException on error
	 */
	public static void stopLauncher() throws InterruptedException {
		if (LAUNCHER != null) {
			LAUNCHER.stop();
		}
	}

}
