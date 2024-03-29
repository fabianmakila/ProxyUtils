package fi.fabianadrian.proxyutils.common.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import fi.fabianadrian.proxyutils.common.ProxyUtils;

public abstract class ProxyUtilsCommand {
	protected final ProxyUtils proxyUtils;
	protected final CommandManager<Commander> manager;
	private final String permission;
	private final Command.Builder<Commander> builder;

	public ProxyUtilsCommand(ProxyUtils proxyUtils, String commandName, String... commandAliases) {
		this.proxyUtils = proxyUtils;
		this.manager = proxyUtils.platform().commandManager();

		this.permission = "proxyutils.command." + commandName;

		this.builder = this.manager.commandBuilder(
				commandName,
				commandAliases
		);
	}

	public abstract void register();

	protected Command.Builder<Commander> builder() {
		return this.builder.permission(this.permission);
	}

	protected Command.Builder<Commander> subCommand(String literal) {
		return this.builder.literal(literal).permission(this.permission + "." + literal);
	}
}
