package fi.fabianadrian.proxyutils.common.platform;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeecordPlatformPlayer implements PlatformPlayer {
    private final ProxiedPlayer player;

    public BungeecordPlatformPlayer(ProxiedPlayer player) {
        this.player = player;
    }

    @Override
    public String name() {
        return this.player.getName();
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.player.hasPermission(permission);
    }
}
