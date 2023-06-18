package fi.fabianadrian.proxyutils.common.locale;

import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.config.ProxyUtilsConfig;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;
import java.util.List;

public class Messages {
    private final ProxyUtils proxyUtils;
    private final TextColor defaultColor = TextColor.fromHexString("#ffffff");
    private ProxyUtilsConfig.Colors colors;

    public Messages(ProxyUtils proxyUtils) {
        this.proxyUtils = proxyUtils;
    }

    public void reload() {
        this.colors = this.proxyUtils.configManager().mainConfig().colors();
    }

    public Component message(MessageKey messageKey, String... args) {
        TextColor primaryColor = primaryColor(messageKey.messageType);
        TextColor secondaryColor = secondaryColor(messageKey.messageType);

        List<Component> coloredArgs = new ArrayList<>();
        for (String arg : args) {
            coloredArgs.add(Component.text(arg, secondaryColor));
        }

        return Component.translatable(messageKey.key, primaryColor).args(coloredArgs);
    }

    public Component list(List<String> items) {
        Component linePrefix = Component.text("- ", TextColor.fromHexString(this.colors.gray()));

        List<Component> lines = new ArrayList<>();

        for (String item : items) {
            lines.add(linePrefix.append(Component.text(item, TextColor.fromHexString(this.colors.primary()))));
        }

        return Component.join(JoinConfiguration.separator(Component.newline()), lines);
    }

    public void sendMessage(Audience audience, MessageKey messageKey, String... args) {
        audience.sendMessage(message(messageKey, args));
    }

    private TextColor primaryColor(MessageKey.MessageType messageType) {
        switch (messageType) {
            case INFO:
                return TextColor.fromHexString(this.colors.primary());
            case WARNING:
                return TextColor.fromHexString(this.colors.warning());
            case ERROR:
                return TextColor.fromHexString(this.colors.error());
        }

        return defaultColor;
    }

    private TextColor secondaryColor(MessageKey.MessageType messageType) {
        switch (messageType) {
            case INFO:
                return TextColor.fromHexString(this.colors.accent());
            case WARNING:
                return TextColor.fromHexString(this.colors.error());
            case ERROR:
                return TextColor.fromHexString(this.colors.warning());
        }

        return defaultColor;
    }
}
