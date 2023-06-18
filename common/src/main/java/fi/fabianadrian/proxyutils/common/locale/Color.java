package fi.fabianadrian.proxyutils.common.locale;

import net.kyori.adventure.text.format.TextColor;

public enum Color {
    PRIMARY("#facc15"),

    WHITE("#ffffff"),
    BLACK("#000000"),
    GRAY("#aaaaaa"),

    RED("#ff5555"),
    YELLOW("ffff55"),
    GREEN("55ff55");

    public final TextColor textColor;

    Color(String hexString) {
        this.textColor = TextColor.fromHexString(hexString);
    }
}
