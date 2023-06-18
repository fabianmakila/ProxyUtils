package fi.fabianadrian.proxyutils.common.command.processor;

import cloud.commandframework.context.CommandContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessingContext;
import cloud.commandframework.execution.preprocessor.CommandPreprocessor;
import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsContextKeys;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class ProxyUtilsCommandPreprocessor<C> implements CommandPreprocessor<C> {
    private final ProxyUtils proxyUtils;

    public ProxyUtilsCommandPreprocessor(final ProxyUtils proxyUtils) {
        this.proxyUtils = proxyUtils;
    }

    @Override
    public void accept(@NonNull CommandPreprocessingContext<C> context) {
        CommandContext<C> commandContext = context.getCommandContext();
        commandContext.store(ProxyUtilsContextKeys.PLATFORM_KEY, this.proxyUtils.platform());
    }
}
