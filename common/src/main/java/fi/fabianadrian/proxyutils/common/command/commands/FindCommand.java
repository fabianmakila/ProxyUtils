package fi.fabianadrian.proxyutils.common.command.commands;

import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsCommand;
import fi.fabianadrian.proxyutils.common.command.parser.PlayerParser;
import fi.fabianadrian.proxyutils.common.locale.Color;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import org.incendo.cloud.context.CommandContext;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

public class FindCommand extends ProxyUtilsCommand {
	public FindCommand(ProxyUtils proxyUtils) {
		super(proxyUtils, "find");
	}

	@Override
	public void register() {
		this.manager.command(
				this.builder().required("player", PlayerParser.playerParser()).handler(this::executeFind)
		);
	}

	public void executeFind(CommandContext<Commander> ctx) {
		PlatformPlayer player = ctx.get("player");

		if (player.currentServer().isEmpty()) {
			ctx.sender().sendMessage(translatable("proxyutils.command.find.error", Color.RED.textColor)
					.arguments(text(player.name()))
			);
			return;
		}

		ctx.sender().sendMessage(translatable("proxyutils.command.find", Color.GREEN.textColor)
				.arguments(text(player.name()), text(player.currentServer().get().name()))
		);
	}
}
