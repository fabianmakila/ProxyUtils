package fi.fabianadrian.proxyutils.common.locale;

import java.util.Locale;

public enum MessageKey {
    COMMAND_ROOT_RELOAD(MessageType.INFO),
    COMMAND_SEND_PLAYER(MessageType.INFO),
    COMMAND_SEND_SERVER(MessageType.INFO),
    COMMAND_SEND_ALL(MessageType.INFO),
    COMMAND_STAFF_LIST(MessageType.INFO),
    GEOIP2_DISALLOWED(MessageType.ERROR);

    public final MessageType messageType;
    public final String key;

    MessageKey(MessageType messageType) {
        this.messageType = messageType;
        this.key = "proxyutils." + this.name().toLowerCase(Locale.ROOT).replace("_", ".");
    }

    enum MessageType {
        INFO, WARNING, ERROR
    }
}
