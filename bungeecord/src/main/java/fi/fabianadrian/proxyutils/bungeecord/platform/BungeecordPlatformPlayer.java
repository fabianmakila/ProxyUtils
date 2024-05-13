package fi.fabianadrian.proxyutils.bungeecord.platform;

import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Optional;

public record BungeecordPlatformPlayer(ProxiedPlayer player) implements PlatformPlayer {
	@Override
	public String name() {
		return this.player.getName();
	}

	@Override
	public Optional<PlatformServer> currentServer() {
		return Optional.of(new BungeecordPlatformServer(this.player.getServer().getInfo()));
	}

	@Override
	public boolean hasPermission(String permission) {
		return this.player.hasPermission(permission);
	}
}
