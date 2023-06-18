package fi.fabianadrian.proxyutils.common.command.commands;

import cloud.commandframework.context.CommandContext;
import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsCommand;
import fi.fabianadrian.proxyutils.common.command.argument.PlayerArgument;
import fi.fabianadrian.proxyutils.common.command.argument.ServerArgument;
import fi.fabianadrian.proxyutils.common.locale.Color;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;

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
                subCommand("player").argument(PlayerArgument.of(PLAYER_KEY)).argument(ServerArgument.of(DESTINATION_KEY)).handler(this::executeSendPlayer)
        );
        this.manager.command(
                subCommand("server").argument(ServerArgument.of(SERVER_KEY)).argument(ServerArgument.of(DESTINATION_KEY)).handler(this::executeSendServer)
        );
        this.manager.command(
                subCommand("all").argument(ServerArgument.of(DESTINATION_KEY)).handler(this::executeSendAll)
        );
    }

    private void executeSendPlayer(CommandContext<Commander> ctx) {
        PlatformPlayer player = ctx.get(PLAYER_KEY);
        PlatformServer server = ctx.get(DESTINATION_KEY);

        this.proxyUtils.platform().transferPlayer(player, server);

        ctx.getSender().sendMessage(translatable("proxyutils.command.send.player", Color.GREEN.textColor)
                .args(text(player.name()), text(server.name()))
        );
    }

    private void executeSendServer(CommandContext<Commander> ctx) {
        PlatformServer server = ctx.get(SERVER_KEY);
        PlatformServer destination = ctx.get(DESTINATION_KEY);

        this.proxyUtils.platform().transferPlayers(server.players(), destination);

        ctx.getSender().sendMessage(
                translatable("proxyutils.command.send.server", Color.GREEN.textColor)
                        .args(text(server.name()), text(destination.name()))
        );
    }

    private void executeSendAll(CommandContext<Commander> ctx) {
        PlatformServer destination = ctx.get(DESTINATION_KEY);

        this.proxyUtils.platform().transferPlayers(
                this.proxyUtils.platform().onlinePlayers(),
                destination
        );

        ctx.getSender().sendMessage(
                translatable("proxyutils.command.send.all", Color.GREEN.textColor)
                        .args(text(destination.name()))
        );
    }
}
