package fi.fabianadrian.proxyutils.bungeecord.listener;

import fi.fabianadrian.proxyutils.bungeecord.ProxyUtilsBungeecord;
import fi.fabianadrian.proxyutils.common.locale.MessageKey;
import fi.fabianadrian.proxyutils.common.locale.Messages;
import fi.fabianadrian.proxyutils.common.service.GeoIP2Service;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginListener implements Listener {
    private final GeoIP2Service geoIP2Service;
    private final Messages messages;

    public LoginListener(ProxyUtilsBungeecord plugin) {
        this.geoIP2Service = plugin.proxyUtils().geoIP2Service();
        this.messages = plugin.proxyUtils().messages();
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

        player.disconnect(BungeeComponentSerializer.get().serialize(this.messages.message(MessageKey.GEOIP2_DISALLOWED)));
    }
}
