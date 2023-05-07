package fi.fabianadrian.proxyutils.common.config;

import space.arim.dazzleconf.annote.ConfDefault;
import space.arim.dazzleconf.annote.SubSection;

import java.util.List;

public interface ProxyUtilsConfig {

    static List<String> emptyList() {
        return List.of();
    }

    GeoIP2 geoIP2();

    @SubSection
    interface GeoIP2 {
        @ConfDefault.DefaultBoolean(false)
        boolean geolite();
        @ConfDefault.DefaultInteger(0)
        int accountID();

        @ConfDefault.DefaultString("")
        String licenceKey();

        @ConfDefault.DefaultStrings({})
        List<String> countryCodes();

        @ConfDefault.DefaultString("BLACKLIST")
        ListType listType();

        enum ListType {
            BLACKLIST, WHITELIST
        }
    }
}
