package fi.fabianadrian.proxyutils.common.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.locale.Messages;

public abstract class ProxyUtilsCommand {
    protected final ProxyUtils proxyUtils;
    protected final CommandManager<Commander> manager;
    protected final Messages messages;
    private final String commandName;
    private final String[] commandAliases;
    private final String permission;

    public ProxyUtilsCommand(ProxyUtils proxyUtils, String commandName, String... commandAliases) {
        this.proxyUtils = proxyUtils;
        this.manager = proxyUtils.platform().commandManager();
        this.messages = proxyUtils.messages();

        this.commandName = commandName;
        this.commandAliases = commandAliases;

        this.permission = "proxyutils.command." + commandName;
    }

    public abstract void register();

    protected Command.Builder<Commander> builder() {
        return this.manager.commandBuilder(
            this.commandName,
            this.commandAliases
        ).permission(this.permission);
    }

    protected Command.Builder<Commander> subCommand(String literal) {
        return this.builder().literal(literal).permission(this.permission + "." + literal);
    }
}
