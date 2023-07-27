package fi.fabianadrian.proxyutils.common.command.commands;

import cloud.commandframework.context.CommandContext;
import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsCommand;
import fi.fabianadrian.proxyutils.common.command.argument.PlayerArgument;
import fi.fabianadrian.proxyutils.common.locale.Color;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.translatable;

public class FindCommand extends ProxyUtilsCommand {
	public FindCommand(ProxyUtils proxyUtils) {
		super(proxyUtils, "find");
	}

	@Override
	public void register() {
		this.manager.command(
				this.builder().argument(PlayerArgument.of("player")).handler(this::executeFind)
		);
	}

	public void executeFind(CommandContext<Commander> ctx) {
		PlatformPlayer player = ctx.get("player");

		if (player.currentServer().isEmpty()) {
			ctx.getSender().sendMessage(translatable("proxyutils.command.find.error", Color.RED.textColor)
					.args(text(player.name()))
			);
			return;
		}

		ctx.getSender().sendMessage(translatable("proxyutils.command.find", Color.GREEN.textColor)
				.args(text(player.name()), text(player.currentServer().get().name()))
		);
	}
}
