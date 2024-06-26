package fi.fabianadrian.proxyutils.common.command.processor;

import fi.fabianadrian.proxyutils.common.ProxyUtils;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsContextKeys;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessingContext;
import org.incendo.cloud.execution.preprocessor.CommandPreprocessor;

public final class ProxyUtilsCommandPreprocessor<C> implements CommandPreprocessor<C> {
	private final ProxyUtils proxyUtils;

	public ProxyUtilsCommandPreprocessor(final ProxyUtils proxyUtils) {
		this.proxyUtils = proxyUtils;
	}

	@Override
	public void accept(@NonNull CommandPreprocessingContext<C> context) {
		CommandContext<C> commandContext = context.commandContext();
		commandContext.store(ProxyUtilsContextKeys.PLATFORM_KEY, this.proxyUtils.platform());
	}
}
