package fi.fabianadrian.proxyutils.bungeecord.command;

import fi.fabianadrian.proxyutils.common.command.Commander;
import net.kyori.adventure.audience.Audience;
import net.md_5.bungee.api.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BungeecordCommander implements Commander {
    private final CommandSender commandSender;
    private final Audience audience;

    public BungeecordCommander(CommandSender commandSender, Audience audience) {
        this.commandSender = commandSender;
        this.audience = audience;
    }

    public CommandSender commandSender() {
        return this.commandSender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.commandSender.hasPermission(permission);
    }

    @Override
    public @NotNull Audience audience() {
        return this.audience;
    }
}
