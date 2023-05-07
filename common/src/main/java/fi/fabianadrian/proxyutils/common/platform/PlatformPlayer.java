package fi.fabianadrian.proxyutils.common.player;

public interface PlatformPlayer {
    String name();
    boolean hasPermission(String permission);
}
