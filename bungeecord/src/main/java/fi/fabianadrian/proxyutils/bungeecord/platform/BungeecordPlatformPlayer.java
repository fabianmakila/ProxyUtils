package fi.fabianadrian.proxyutils.bungeecord.platform;

import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Optional;

public class BungeecordPlatformPlayer implements PlatformPlayer {
	private final ProxiedPlayer player;

	public BungeecordPlatformPlayer(ProxiedPlayer player) {
		this.player = player;
	}

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

	public ProxiedPlayer player() {
		return this.player;
	}
}
