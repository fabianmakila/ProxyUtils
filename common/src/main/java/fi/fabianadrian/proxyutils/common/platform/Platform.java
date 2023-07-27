package fi.fabianadrian.proxyutils.common.platform;

import cloud.commandframework.CommandManager;
import fi.fabianadrian.proxyutils.common.command.Commander;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.List;

public interface Platform {
	Logger logger();

	Path dataDirectory();

	CommandManager<Commander> commandManager();

	List<PlatformPlayer> onlinePlayers();

	List<PlatformServer> servers();

	void transferPlayer(PlatformPlayer player, PlatformServer destination);

	void transferPlayers(List<PlatformPlayer> players, PlatformServer destination);
}
