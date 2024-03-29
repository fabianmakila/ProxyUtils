package fi.fabianadrian.proxyutils.common.config;

import fi.fabianadrian.proxyutils.common.ProxyUtils;
import org.slf4j.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import space.arim.dazzleconf.ConfigurationOptions;
import space.arim.dazzleconf.error.ConfigFormatSyntaxException;
import space.arim.dazzleconf.error.InvalidConfigException;
import space.arim.dazzleconf.ext.snakeyaml.CommentMode;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions;
import space.arim.dazzleconf.helper.ConfigurationHelper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

public final class ConfigManager {
	private final Logger logger;
	private final ConfigurationHelper<ProxyUtilsConfig> mainConfigHelper;

	private volatile ProxyUtilsConfig mainConfigData;

	public ConfigManager(ProxyUtils proxyUtils) {
		this.logger = proxyUtils.platform().logger();

		SnakeYamlOptions yamlOptions = new SnakeYamlOptions.Builder()
				.yamlSupplier(() -> {
					DumperOptions dumperOptions = new DumperOptions();
					// Enables comments
					dumperOptions.setProcessComments(true);
					dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
					return new Yaml(dumperOptions);
				})
				.commentMode(CommentMode.fullComments())
				.build();

		Path dataDirectory = proxyUtils.platform().dataDirectory();

		this.mainConfigHelper = new ConfigurationHelper<>(
				dataDirectory,
				"config.yml",
				SnakeYamlConfigurationFactory.create(
						ProxyUtilsConfig.class,
						ConfigurationOptions.defaults(),
						yamlOptions
				)
		);
	}

	public void reload() {
		try {
			this.mainConfigData = this.mainConfigHelper.reloadConfigData();
		} catch (IOException ex) {
			throw new UncheckedIOException(ex);

		} catch (ConfigFormatSyntaxException ex) {
			this.mainConfigData = this.mainConfigHelper.getFactory().loadDefaults();
			this.logger.error(
					"The yaml syntax in your configuration is invalid. " +
							"Check your YAML syntax with a tool such as https://yaml-online-parser.appspot.com/",
					ex
			);
		} catch (InvalidConfigException ex) {
			this.mainConfigData = this.mainConfigHelper.getFactory().loadDefaults();
			this.logger.error(
					"One of the values in your configuration is not valid. " +
							"Check to make sure you have specified the right data types.",
					ex
			);
		}
	}

	public ProxyUtilsConfig mainConfig() {
		ProxyUtilsConfig configData = this.mainConfigData;
		if (configData == null) {
			throw new IllegalStateException("Configuration has not been loaded yet");
		}
		return configData;
	}
}
