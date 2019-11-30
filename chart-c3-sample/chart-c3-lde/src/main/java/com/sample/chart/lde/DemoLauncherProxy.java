package com.sample.chart.lde;

import com.github.bordertech.lde.api.LdeLauncher;

/**
 * Start Tomcat Server.
 *
 * @author Jonathan Austin
 */
@SuppressWarnings("HideUtilityClassConstructor")
public final class DemoLauncherProxy {

	public static void main(final String[] args) {
		LdeLauncher.launchServer();
	}
}
