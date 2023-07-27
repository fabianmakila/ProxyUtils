package fi.fabianadrian.proxyutils.velocity.platform;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;

import java.util.List;
import java.util.stream.Collectors;

public class VelocityPlatformServer implements PlatformServer {
	private final RegisteredServer server;

	public VelocityPlatformServer(RegisteredServer server) {
		this.server = server;
	}

	@Override
	public String name() {
		return this.server.getServerInfo().getName();
	}

	@Override
	public List<PlatformPlayer> players() {
		return this.server.getPlayersConnected().stream().map(VelocityPlatformPlayer::new).collect(Collectors.toList());
	}

	public RegisteredServer server() {
		return this.server;
	}
}
