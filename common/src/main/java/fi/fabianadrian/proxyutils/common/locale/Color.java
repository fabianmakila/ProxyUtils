package fi.fabianadrian.proxyutils.common.locale;

import net.kyori.adventure.text.format.TextColor;

public enum Color {
    PRIMARY("#facc15"),
    WHITE("#ffffff"),
    GRAY("#6b7280"),
    RED("#ef4444");

    public final TextColor textColor;

    Color(String hexString) {
        this.textColor = TextColor.fromHexString(hexString);
    }
}
