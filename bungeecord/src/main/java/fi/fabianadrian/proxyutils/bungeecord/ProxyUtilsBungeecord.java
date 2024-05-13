package fi.fabianadrian.proxyutils.bungeecord;

import fi.fabianadrian.proxyutils.bungeecord.command.BungeecordCommander;
import fi.fabianadrian.proxyutils.bungeecord.platform.BungeecordPlatformPlayer;
import fi.fabianadrian.proxyutils.bungeecord.platform.BungeecordPlatformServer;
import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.platform.Platform;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.bungee.BungeeCommandManager;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ProxyUtilsBungeecord extends Plugin implements Platform {
	private BungeeAudiences adventure;
	private BungeeCommandManager<Commander> commandManager;

	@Override
	public void onEnable() {
		this.adventure = BungeeAudiences.create(this);
		createCommandManager();
		new ProxyUtils(this);
		new Metrics(this, 18438);
	}

	@Override
	public void onDisable() {
		if (this.adventure != null) {
			this.adventure.close();
			this.adventure = null;
		}
	}

	@Override
	public Logger logger() {
		return LoggerFactory.getLogger(this.getDescription().getName());
	}

	@Override
	public Path dataDirectory() {
		return getDataFolder().toPath();
	}

	@Override
	public BungeeCommandManager<Commander> commandManager() {
		return this.commandManager;
	}

	@Override
	public List<PlatformPlayer> onlinePlayers() {
		return getProxy().getPlayers().stream().map(BungeecordPlatformPlayer::new).collect(Collectors.toList());
	}

	@Override
	public List<PlatformServer> servers() {
		return this.getProxy().getServers().values().stream().map(BungeecordPlatformServer::new).collect(Collectors.toList());
	}

	@Override
	public void transferPlayer(PlatformPlayer player, PlatformServer destination) {
		((BungeecordPlatformPlayer) player).player().connect(((BungeecordPlatformServer) destination).serverInfo());
	}

	@Override
	public void transferPlayers(List<PlatformPlayer> players, PlatformServer destination) {
		ServerInfo destinationServerInfo = ((BungeecordPlatformServer) destination).serverInfo();
		players.forEach(player -> ((BungeecordPlatformPlayer) player).player().connect(destinationServerInfo));
	}

	private void createCommandManager() {
		SenderMapper<CommandSender, Commander> senderMapper = SenderMapper.create(
				commandSource -> new BungeecordCommander(commandSource, this.adventure.sender(commandSource)),
				commander -> ((BungeecordCommander) commander).commandSender()
		);

		this.commandManager = new BungeeCommandManager<>(
				this,
				ExecutionCoordinator.simpleCoordinator(),
				senderMapper
		);
	}
}
