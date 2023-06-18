package fi.fabianadrian.proxyutils.common.command.commands;

import cloud.commandframework.context.CommandContext;
import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsCommand;
import fi.fabianadrian.proxyutils.common.command.argument.PlayerArgument;
import fi.fabianadrian.proxyutils.common.command.argument.ServerArgument;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;
import net.kyori.adventure.text.Component;

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
                this.builder().literal("player").argument(PlayerArgument.of(PLAYER_KEY)).argument(ServerArgument.of(DESTINATION_KEY)).handler(this::executeSendPlayer)
        );
        this.manager.command(
                this.builder().literal("server").argument(ServerArgument.of(SERVER_KEY)).argument(ServerArgument.of(DESTINATION_KEY)).handler(this::executeSendServer)
        );
        this.manager.command(
                this.builder().literal("all").argument(ServerArgument.of(DESTINATION_KEY)).handler(this::executeSendAll)
        );
    }

    private void executeSendPlayer(CommandContext<Commander> ctx) {
        this.proxyUtils.platform().transferPlayer(ctx.get(PLAYER_KEY), ctx.get(DESTINATION_KEY));
        ctx.getSender().sendMessage(
                Component.translatable("proxychat.command.send.player")
        );
    }

    private void executeSendServer(CommandContext<Commander> ctx) {
        PlatformServer server = ctx.get(SERVER_KEY);
        this.proxyUtils.platform().transferPlayers(server.players(), ctx.get(DESTINATION_KEY));
        ctx.getSender().sendMessage(
                Component.translatable("proxychat.command.send.server")
        );
    }

    private void executeSendAll(CommandContext<Commander> ctx) {
        this.proxyUtils.platform().transferPlayers(
                this.proxyUtils.platform().onlinePlayers(),
                ctx.get(DESTINATION_KEY)
        );
        ctx.getSender().sendMessage(
                Component.translatable("proxychat.command.send.all")
        );
    }
}
