package fi.fabianadrian.proxyutils.velocity.listener;

import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import fi.fabianadrian.proxyutils.common.locale.Color;
import fi.fabianadrian.proxyutils.common.service.GeoIP2Service;
import fi.fabianadrian.proxyutils.velocity.ProxyUtilsVelocity;
import net.kyori.adventure.text.Component;

public class LoginListener {
	private final GeoIP2Service geoIP2Service;

	public LoginListener(ProxyUtilsVelocity proxyUtilsVelocity) {
		this.geoIP2Service = proxyUtilsVelocity.proxyUtils().geoIP2Service();
	}

	@Subscribe
	public void onLogin(LoginEvent event) {
		if (event.getPlayer().hasPermission("proxyutils.geoip2.bypass")) {
			return;
		}

		if (this.geoIP2Service.isAllowedToJoin(event.getPlayer().getRemoteAddress().getAddress())) {
			return;
		}

		event.setResult(ResultedEvent.ComponentResult.denied(
				Component.translatable("proxyutils.geoip2.disallowed", Color.RED.textColor)
		));
	}
}
