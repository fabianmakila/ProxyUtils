package fi.fabianadrian.proxyutils.common.command;

import net.kyori.adventure.audience.ForwardingAudience;

public interface Commander extends ForwardingAudience.Single {
    boolean hasPermission(String permission);
}
