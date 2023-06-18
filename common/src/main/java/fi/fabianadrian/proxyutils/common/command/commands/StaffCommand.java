package fi.fabianadrian.proxyutils.common.command.commands;

import cloud.commandframework.context.CommandContext;
import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsCommand;
import fi.fabianadrian.proxyutils.common.locale.MessageKey;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;

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

        ctx.getSender().sendMessage(
                this.messages.message(MessageKey.COMMAND_STAFF_LIST).appendNewline().append(this.messages.list(onlineStaffNames))
        );
    }
}
