package com.sample.client.lde;

import com.github.bordertech.lde.api.LdeLauncher;

/**
 * Start Tomcat Server.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@SuppressWarnings("HideUtilityClassConstructor")
public final class DemoLauncherProxy {

	public static void main(final String[] args) {
		LdeLauncher.launchServer();
	}
}
