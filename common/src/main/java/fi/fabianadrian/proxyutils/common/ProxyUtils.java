package fi.fabianadrian.proxyutils.common;

import fi.fabianadrian.proxyutils.common.command.ProxyUtilsCommand;
import fi.fabianadrian.proxyutils.common.command.commands.FindCommand;
import fi.fabianadrian.proxyutils.common.command.commands.RootCommand;
import fi.fabianadrian.proxyutils.common.command.commands.SendCommand;
import fi.fabianadrian.proxyutils.common.command.commands.StaffCommand;
import fi.fabianadrian.proxyutils.common.command.processor.ProxyUtilsCommandPreprocessor;
import fi.fabianadrian.proxyutils.common.config.ConfigManager;
import fi.fabianadrian.proxyutils.common.locale.TranslationManager;
import fi.fabianadrian.proxyutils.common.platform.Platform;
import fi.fabianadrian.proxyutils.common.service.GeoIP2Service;

import java.util.stream.Stream;

public final class ProxyUtils {
    private final Platform platform;
    private final ConfigManager configManager;
    private final TranslationManager translationManager;
    private final GeoIP2Service geoIP2Service;

    public ProxyUtils(Platform platform) {
        this.platform = platform;

        this.configManager = new ConfigManager(this);
        this.configManager.reload();

        this.translationManager = new TranslationManager(this);
        this.translationManager.reload();

        this.geoIP2Service = new GeoIP2Service(this);
        this.geoIP2Service.reload();

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

    public ConfigManager configManager() {
        return configManager;
    }

    public GeoIP2Service geoIP2Service() {
        return this.geoIP2Service;
    }

    public void reload() {
        this.configManager.reload();
        this.translationManager.reload();
        this.geoIP2Service.reload();
    }
}
