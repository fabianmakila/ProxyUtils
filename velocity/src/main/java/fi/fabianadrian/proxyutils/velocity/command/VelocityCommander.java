package fi.fabianadrian.proxyutils.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import fi.fabianadrian.proxyutils.common.command.Commander;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public class VelocityCommander implements Commander {
	private final CommandSource commandSource;

	public VelocityCommander(CommandSource commandSource) {
		this.commandSource = commandSource;
	}

	public CommandSource commandSource() {
		return this.commandSource;
	}

	@Override
	public boolean hasPermission(String permission) {
		return this.commandSource.hasPermission(permission);
	}

	@Override
	public @NotNull Audience audience() {
		return this.commandSource;
	}
}
