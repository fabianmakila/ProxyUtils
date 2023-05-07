package fi.fabianadrian.proxyutils.velocity.platform;

import com.velocitypowered.api.proxy.Player;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;

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
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }
}
