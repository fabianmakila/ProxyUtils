package fi.fabianadrian.proxyutils.velocity.platform;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;

import java.util.List;
import java.util.stream.Collectors;

public record VelocityPlatformServer(RegisteredServer server) implements PlatformServer {
	@Override
	public String name() {
		return this.server.getServerInfo().getName();
	}

	@Override
	public List<PlatformPlayer> players() {
		return this.server.getPlayersConnected().stream().map(VelocityPlatformPlayer::new).collect(Collectors.toList());
	}
}
