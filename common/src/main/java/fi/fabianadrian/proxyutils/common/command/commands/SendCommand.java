package fi.fabianadrian.proxyutils.common.command.commands;

import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsCommand;
import fi.fabianadrian.proxyutils.common.locale.Color;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;
import org.incendo.cloud.context.CommandContext;

import static fi.fabianadrian.proxyutils.common.command.parser.PlayerParser.playerParser;
import static fi.fabianadrian.proxyutils.common.command.parser.ServerParser.serverParser;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

public class SendCommand extends ProxyUtilsCommand {
	private static final String PLAYER_KEY = "player";
	private static final String SERVER_KEY = "server";
	private static final String DESTINATION_KEY = "destination";

	public SendCommand(ProxyUtils proxyUtils) {
		super(proxyUtils, "send");
	}

	@Override
	public void register() {
		this.manager.command(
				subCommand("player").required(PLAYER_KEY, playerParser()).required(DESTINATION_KEY, serverParser()).handler(this::executeSendPlayer)
		);
		this.manager.command(
				subCommand("server").required(SERVER_KEY, serverParser()).required(DESTINATION_KEY, serverParser()).handler(this::executeSendServer)
		);
		this.manager.command(
				subCommand("all").required(DESTINATION_KEY, serverParser()).handler(this::executeSendAll)
		);
	}

	private void executeSendPlayer(CommandContext<Commander> ctx) {
		PlatformPlayer player = ctx.get(PLAYER_KEY);
		PlatformServer server = ctx.get(DESTINATION_KEY);

		this.proxyUtils.platform().transferPlayer(player, server);

		ctx.sender().sendMessage(translatable("proxyutils.command.send.player", Color.GREEN.textColor)
				.arguments(text(player.name()), text(server.name()))
		);
	}

	private void executeSendServer(CommandContext<Commander> ctx) {
		PlatformServer server = ctx.get(SERVER_KEY);
		PlatformServer destination = ctx.get(DESTINATION_KEY);

		this.proxyUtils.platform().transferPlayers(server.players(), destination);

		ctx.sender().sendMessage(
				translatable("proxyutils.command.send.server", Color.GREEN.textColor)
						.arguments(text(server.name()), text(destination.name()))
		);
	}

	private void executeSendAll(CommandContext<Commander> ctx) {
		PlatformServer destination = ctx.get(DESTINATION_KEY);

		this.proxyUtils.platform().transferPlayers(
				this.proxyUtils.platform().onlinePlayers(),
				destination
		);

		ctx.sender().sendMessage(
				translatable("proxyutils.command.send.all", Color.GREEN.textColor)
						.arguments(text(destination.name()))
		);
	}
}
