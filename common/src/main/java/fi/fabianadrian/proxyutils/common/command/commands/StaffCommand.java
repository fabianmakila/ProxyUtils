package fi.fabianadrian.proxyutils.common.command.commands;

import cloud.commandframework.context.CommandContext;
import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsCommand;
import fi.fabianadrian.proxyutils.common.locale.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;

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
        List<Component> onlineStaffAsComponents = this.proxyUtils.platform().onlinePlayers().stream()
            .filter(player -> player.hasPermission("proxychat.staff"))
            .map(player -> Component.text("- ", Color.GRAY.textColor).append(Component.text(player.name(), Color.WHITE.textColor)))
            .collect(Collectors.toList());

        ctx.getSender().sendMessage(
            Component.translatable("proxyutils.command.staff.header", Color.PRIMARY.textColor)
                .append(Component.newline())
                .append(
                    Component.join(JoinConfiguration.separator(Component.newline()), onlineStaffAsComponents)
                )
        );
    }
}
