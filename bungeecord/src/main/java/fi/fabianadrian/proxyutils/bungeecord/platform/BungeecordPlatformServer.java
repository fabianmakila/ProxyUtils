package fi.fabianadrian.proxyutils.bungeecord.platform;

import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.List;
import java.util.stream.Collectors;

public class BungeecordPlatformServer implements PlatformServer {
	private final ServerInfo serverInfo;

	public BungeecordPlatformServer(ServerInfo serverInfo) {
		this.serverInfo = serverInfo;
	}

	@Override
	public String name() {
		return this.serverInfo.getName();
	}

	@Override
	public List<PlatformPlayer> players() {
		return this.serverInfo.getPlayers().stream().map(BungeecordPlatformPlayer::new).collect(Collectors.toList());
	}

	public ServerInfo serverInfo() {
		return this.serverInfo;
	}
}
