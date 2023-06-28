package fi.fabianadrian.proxyutils.common.platform;

import java.util.Optional;

public interface PlatformPlayer {
    String name();

    Optional<PlatformServer> currentServer();

    boolean hasPermission(String permission);
}
