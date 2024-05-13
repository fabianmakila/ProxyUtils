package fi.fabianadrian.proxyutils.common.command;

import fi.fabianadrian.proxyutils.common.platform.Platform;
import org.incendo.cloud.key.CloudKey;

public class ProxyUtilsContextKeys {

	public static final CloudKey<Platform> PLATFORM_KEY = CloudKey.of(
			"Platform",
			Platform.class
	);

	private ProxyUtilsContextKeys() {
	}
}
