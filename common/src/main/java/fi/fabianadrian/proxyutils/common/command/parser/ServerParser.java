package fi.fabianadrian.proxyutils.common.command.parser;

import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsContextKeys;
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.caption.CaptionVariable;
import org.incendo.cloud.component.CommandComponent;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.incendo.cloud.exception.parsing.ParserException;
import org.incendo.cloud.minecraft.extras.caption.TranslatableCaption;
import org.incendo.cloud.parser.ArgumentParseResult;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.suggestion.BlockingSuggestionProvider;

import java.util.List;
import java.util.stream.Collectors;

public final class ServerParser implements ArgumentParser<Commander, PlatformServer>, BlockingSuggestionProvider.Strings<Commander> {
	public static @NonNull ParserDescriptor<Commander, PlatformServer> serverParser() {
		return ParserDescriptor.of(new ServerParser(), PlatformServer.class);
	}

	public static CommandComponent.@NonNull Builder<Commander, PlatformServer> serverComponent() {
		return CommandComponent.<Commander, PlatformServer>builder().parser(serverParser());
	}

	@Override
	public @NonNull ArgumentParseResult<@NonNull PlatformServer> parse(@NonNull CommandContext<@NonNull Commander> context, @NonNull CommandInput input) {
		final String inputString = input.peekString();

		final List<PlatformServer> onlinePlayers = context.get(ProxyUtilsContextKeys.PLATFORM_KEY).servers();

		for (PlatformServer player : onlinePlayers) {
			if (player.name().equals(inputString)) {
				input.readString();
				return ArgumentParseResult.success(player);
			}
		}
		return ArgumentParseResult.failure(new ServerParseException(inputString, context));
	}

	@Override
	public @NonNull Iterable<@NonNull String> stringSuggestions(@NonNull CommandContext<Commander> context, @NonNull CommandInput input) {
		final List<PlatformServer> onlinePlayers = context.get(ProxyUtilsContextKeys.PLATFORM_KEY).servers();
		return onlinePlayers.stream().map(PlatformServer::name).collect(Collectors.toList());
	}

	public static final class ServerParseException extends ParserException {
		private ServerParseException(
				final @NonNull String input,
				final @NonNull CommandContext<?> context
		) {
			super(
					PlatformServer.class,
					context,
					TranslatableCaption.translatableCaption("argument.parse.failure.server"),
					CaptionVariable.of("input", input)
			);
		}
	}
}
