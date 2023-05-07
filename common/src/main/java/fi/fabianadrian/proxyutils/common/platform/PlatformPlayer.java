package fi.fabianadrian.proxyutils.common.platform;

public interface PlatformPlayer {
    String name();

    boolean hasPermission(String permission);
}
