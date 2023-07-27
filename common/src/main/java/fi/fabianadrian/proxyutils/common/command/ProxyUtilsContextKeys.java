package fi.fabianadrian.proxyutils.common.command;

import cloud.commandframework.keys.CloudKey;
import cloud.commandframework.keys.SimpleCloudKey;
import fi.fabianadrian.proxyutils.common.platform.Platform;
import io.leangen.geantyref.TypeToken;

public class ProxyUtilsContextKeys {

	public static final CloudKey<Platform> PLATFORM_KEY = SimpleCloudKey.of(
			"Platform",
			TypeToken.get(Platform.class)
	);

	private ProxyUtilsContextKeys() {
	}
}
