package fi.fabianadrian.proxyutils.common.listener;

import fi.fabianadrian.proxyutils.common.ProxyUtilsBungeecord;
import fi.fabianadrian.proxyutils.common.locale.Color;
import fi.fabianadrian.proxyutils.common.service.GeoIP2Service;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {
    private final GeoIP2Service geoIP2Service;

    public LoginListener(ProxyUtilsBungeecord plugin) {
        this.geoIP2Service = plugin.proxyUtils().geoIP2Service();
    }

    @EventHandler
    public void onPostJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (player.hasPermission("proxyutils.geoip2.bypass")) {
            return;
        }

        if (this.geoIP2Service.isAllowedToJoin(event.getPlayer().getAddress().getAddress())) {
            return;
        }

        player.disconnect(BungeeComponentSerializer.get().serialize(Component.translatable("proxyutils.geoip2.disallowed", Color.RED.textColor)));
    }
}
