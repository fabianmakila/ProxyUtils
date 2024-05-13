package fi.fabianadrian.proxyutils.common;

import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsCommand;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsComponentCaptionFormatter;
import fi.fabianadrian.proxyutils.common.command.commands.FindCommand;
import fi.fabianadrian.proxyutils.common.command.commands.RootCommand;
import fi.fabianadrian.proxyutils.common.command.commands.SendCommand;
import fi.fabianadrian.proxyutils.common.command.commands.StaffCommand;
import fi.fabianadrian.proxyutils.common.command.processor.ProxyUtilsCommandPreprocessor;
import fi.fabianadrian.proxyutils.common.locale.TranslationManager;
import fi.fabianadrian.proxyutils.common.platform.Platform;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.minecraft.extras.AudienceProvider;
import org.incendo.cloud.minecraft.extras.MinecraftExceptionHandler;
import org.incendo.cloud.minecraft.extras.caption.TranslatableCaption;

import java.util.stream.Stream;

public final class ProxyUtils {
	private final Platform platform;
	private final TranslationManager translationManager;

	public ProxyUtils(Platform platform) {
		this.platform = platform;

		this.translationManager = new TranslationManager(this);
		this.translationManager.reload();

		setupCommandManager();
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

	private void setupCommandManager() {
		CommandManager<Commander> manager = this.platform().commandManager();

		manager.registerCommandPreProcessor(new ProxyUtilsCommandPreprocessor<>(this));
		manager.captionRegistry().registerProvider(TranslatableCaption.translatableCaptionProvider());
		AudienceProvider<Commander> audienceProvider = commander -> commander.audience();
		MinecraftExceptionHandler.create(audienceProvider).defaultHandlers().captionFormatter(ProxyUtilsComponentCaptionFormatter.translatable()).registerTo(manager);
	}

	public void reload() {
		this.translationManager.reload();
	}
}
