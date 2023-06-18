package fi.fabianadrian.proxyutils.common.command.commands;

import cloud.commandframework.context.CommandContext;
import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsCommand;
import fi.fabianadrian.proxyutils.common.locale.Color;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StaffCommand extends ProxyUtilsCommand {
    public StaffCommand(ProxyUtils proxyUtils) {
        super(proxyUtils, "staff");
    }

    @Override
    public void register() {
        this.manager.command(this.builder().handler(this::executeStaff));
    }

    public void executeStaff(CommandContext<Commander> ctx) {
        List<String> onlineStaffNames = this.proxyUtils.platform().onlinePlayers().stream()
                .filter(player -> player.hasPermission("proxyutils.staff"))
                .map(PlatformPlayer::name)
                .collect(Collectors.toList());

        ctx.getSender().sendMessage(Component.translatable("proxyutils.command.staff.list", Color.GREEN.textColor)
                .appendNewline()
                .append(this.list(onlineStaffNames))
        );
    }

    private Component list(List<String> items) {
        Component linePrefix = Component.text("- ", Color.GRAY.textColor);

        List<Component> lines = new ArrayList<>();

        for (String item : items) {
            lines.add(linePrefix.append(Component.text(item, Color.WHITE.textColor)));
        }

        return Component.join(JoinConfiguration.separator(Component.newline()), lines);
    }
}
