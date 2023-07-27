package fi.fabianadrian.proxyutils.common.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.record.Country;
import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.config.ProxyUtilsConfig;

import java.net.InetAddress;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class GeoIP2Service {
	private final ProxyUtils proxyUtils;
	private WebServiceClient client;
	private final LoadingCache<InetAddress, Country> cache = Caffeine.newBuilder()
			.maximumSize(1000)
			.expireAfterWrite(24, TimeUnit.HOURS)
			.build(address -> this.client.country(address).getCountry());
	private ProxyUtilsConfig.GeoIP2 config;

	public GeoIP2Service(ProxyUtils proxyUtils) {
		this.proxyUtils = proxyUtils;
	}

	public void reload() {
		this.config = this.proxyUtils.configManager().mainConfig().geoIP2();

		this.client = null;

		if (this.config.licenceKey().isBlank() || this.config.countryCodes().isEmpty()) {
			return;
		}

		if (this.config.geolite()) {
			this.client = new WebServiceClient.Builder(
					config.accountID(),
					config.licenceKey()
			)
					.host("geolite.info")
					.requestTimeout(Duration.ofSeconds(10))
					.build();
		} else {
			this.client = new WebServiceClient.Builder(
					config.accountID(),
					config.licenceKey()
			)
					.requestTimeout(Duration.ofSeconds(10))
					.build();
		}
	}

	public boolean isAllowedToJoin(InetAddress address) {
		if (this.client == null) {
			return true;
		}

		Country country = this.cache.get(address);
		switch (this.config.listType()) {
			case BLACKLIST:
				return !this.config.countryCodes().contains(country.getIsoCode());
			case WHITELIST:
				return this.config.countryCodes().contains(country.getIsoCode());
		}

		return true;
	}
}
