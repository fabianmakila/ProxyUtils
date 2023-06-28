package fi.fabianadrian.proxyutils.velocity.platform;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;

import java.util.Optional;

public class VelocityPlatformPlayer implements PlatformPlayer {
    private final Player player;

    public VelocityPlatformPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String name() {
        return player.getUsername();
    }

    @Override
    public Optional<PlatformServer> currentServer() {
        Optional<ServerConnection> connectionOptional = this.player.getCurrentServer();
        return connectionOptional.map(serverConnection -> new VelocityPlatformServer(serverConnection.getServer()));
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    public Player player() {
        return this.player;
    }
}
