package fi.fabianadrian.proxyutils.velocity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.platform.Platform;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;
import fi.fabianadrian.proxyutils.velocity.command.VelocityCommander;
import fi.fabianadrian.proxyutils.velocity.platform.VelocityPlatformPlayer;
import fi.fabianadrian.proxyutils.velocity.platform.VelocityPlatformServer;
import org.bstats.velocity.Metrics;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.velocity.CloudInjectionModule;
import org.incendo.cloud.velocity.VelocityCommandManager;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Plugin(
		id = "proxyutils",
		name = "ProxyUtils",
		version = "0.2.0",
		url = "https://github.com/fabianmakila/ProxyUtils",
		description = "Common utilities and features for Minecraft proxies.",
		authors = {"FabianAdrian"}
)
public final class ProxyUtilsVelocity implements Platform {
	private final Path dataDirectory;
	private final Metrics.Factory metricsFactory;
	private final ProxyServer server;
	private final Logger logger;
	private final Injector injector;
	private VelocityCommandManager<Commander> commandManager;

	@Inject
	public ProxyUtilsVelocity(
			ProxyServer server,
			Logger logger,
			@DataDirectory Path dataDirectory,
			Metrics.Factory metricsFactory,
			Injector injector
	) {
		this.server = server;
		this.logger = logger;
		this.dataDirectory = dataDirectory;
		this.metricsFactory = metricsFactory;
		this.injector = injector;
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		createCommandManager();
		new ProxyUtils(this);
		this.metricsFactory.make(this, 18439);
	}

	@Override
	public Logger logger() {
		return this.logger;
	}

	@Override
	public Path dataDirectory() {
		return this.dataDirectory;
	}

	@Override
	public VelocityCommandManager<Commander> commandManager() {
		return this.commandManager;
	}

	@Override
	public List<PlatformPlayer> onlinePlayers() {
		return this.server.getAllPlayers().stream().map(VelocityPlatformPlayer::new).collect(Collectors.toList());
	}

	@Override
	public List<PlatformServer> servers() {
		return this.server.getAllServers().stream().map(VelocityPlatformServer::new).collect(Collectors.toList());
	}

	@Override
	public void transferPlayer(PlatformPlayer player, PlatformServer destination) {
		((VelocityPlatformPlayer) player).player().createConnectionRequest(
				((VelocityPlatformServer) destination).server()
		).fireAndForget();
	}

	@Override
	public void transferPlayers(List<PlatformPlayer> players, PlatformServer destination) {
		RegisteredServer destinationServer = ((VelocityPlatformServer) destination).server();
		players.forEach(player -> ((VelocityPlatformPlayer) player).player().createConnectionRequest(
				destinationServer
		).fireAndForget());
	}

	private void createCommandManager() {
		SenderMapper<CommandSource, Commander> senderMapper = SenderMapper.create(
				VelocityCommander::new,
				commander -> ((VelocityCommander) commander).commandSource()
		);

		Injector childInjector = this.injector.createChildInjector(
				new CloudInjectionModule<>(Commander.class, ExecutionCoordinator.simpleCoordinator(), senderMapper)
		);
		this.commandManager = childInjector.getInstance(
				Key.get(new TypeLiteral<>() {
				})
		);
	}
}
