package fi.fabianadrian.proxyutils.common;

import fi.fabianadrian.proxyutils.common.command.ProxyUtilsCommand;
import fi.fabianadrian.proxyutils.common.command.commands.FindCommand;
import fi.fabianadrian.proxyutils.common.command.commands.RootCommand;
import fi.fabianadrian.proxyutils.common.command.commands.SendCommand;
import fi.fabianadrian.proxyutils.common.command.commands.StaffCommand;
import fi.fabianadrian.proxyutils.common.command.processor.ProxyUtilsCommandPreprocessor;
import fi.fabianadrian.proxyutils.common.locale.TranslationManager;
import fi.fabianadrian.proxyutils.common.platform.Platform;

import java.util.stream.Stream;

public final class ProxyUtils {
	private final Platform platform;
	private final TranslationManager translationManager;

	public ProxyUtils(Platform platform) {
		this.platform = platform;

		this.translationManager = new TranslationManager(this);
		this.translationManager.reload();

		this.platform.commandManager().registerCommandPreProcessor(new ProxyUtilsCommandPreprocessor<>(this));
		registerCommands();
	}

	public Platform platform() {
		return platform;
	}

	private void registerCommands() {
		Stream.of(
				new FindCommand(this),
				new RootCommand(this),
				new SendCommand(this),
				new StaffCommand(this)
		).forEach(ProxyUtilsCommand::register);
	}

	public void reload() {
		this.translationManager.reload();
	}
}
