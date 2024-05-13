package fi.fabianadrian.proxyutils.bungeecord.platform;

import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.List;
import java.util.stream.Collectors;

public record BungeecordPlatformServer(ServerInfo serverInfo) implements PlatformServer {
	@Override
	public String name() {
		return this.serverInfo.getName();
	}

	@Override
	public List<PlatformPlayer> players() {
		return this.serverInfo.getPlayers().stream().map(BungeecordPlatformPlayer::new).collect(Collectors.toList());
	}
}
