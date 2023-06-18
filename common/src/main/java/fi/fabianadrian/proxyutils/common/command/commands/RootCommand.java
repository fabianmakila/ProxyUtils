package fi.fabianadrian.proxyutils.common.command.commands;

import cloud.commandframework.context.CommandContext;
import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsCommand;
import fi.fabianadrian.proxyutils.common.locale.Color;
import net.kyori.adventure.text.Component;

public class RootCommand extends ProxyUtilsCommand {
    public RootCommand(ProxyUtils proxyUtils) {
        super(proxyUtils, "proxyutils");
    }

    @Override
    public void register() {
        this.manager.command(
                subCommand("reload").handler(this::executeReload)
        );
    }

    private void executeReload(CommandContext<Commander> ctx) {
        this.proxyUtils.reload();

        ctx.getSender().sendMessage(
                Component.translatable("proxyutils.command.root.reload", Color.GREEN.textColor)
        );
    }
}
