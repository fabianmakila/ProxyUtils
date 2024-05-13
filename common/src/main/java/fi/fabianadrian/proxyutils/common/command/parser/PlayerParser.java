package fi.fabianadrian.proxyutils.common.command.parser;

import fi.fabianadrian.proxyutils.common.command.Commander;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsContextKeys;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
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

public final class PlayerParser implements ArgumentParser<Commander, PlatformPlayer>, BlockingSuggestionProvider.Strings<Commander> {
	public static @NonNull ParserDescriptor<Commander, PlatformPlayer> playerParser() {
		return ParserDescriptor.of(new PlayerParser(), PlatformPlayer.class);
	}

	public static CommandComponent.@NonNull Builder<Commander, PlatformPlayer> playerComponent() {
		return CommandComponent.<Commander, PlatformPlayer>builder().parser(playerParser());
	}

	@Override
	public @NonNull ArgumentParseResult<@NonNull PlatformPlayer> parse(@NonNull CommandContext<@NonNull Commander> context, @NonNull CommandInput input) {
		final String inputString = input.peekString();

		final List<PlatformPlayer> onlinePlayers = context.get(ProxyUtilsContextKeys.PLATFORM_KEY).onlinePlayers();

		for (PlatformPlayer player : onlinePlayers) {
			if (player.name().equals(inputString)) {
				input.readString();
				return ArgumentParseResult.success(player);
			}
		}

		return ArgumentParseResult.failure(new PlayerParseException(inputString, context));
	}

	@Override
	public @NonNull Iterable<@NonNull String> stringSuggestions(@NonNull CommandContext<Commander> context, @NonNull CommandInput input) {
		final List<PlatformPlayer> onlinePlayers = context.get(ProxyUtilsContextKeys.PLATFORM_KEY).onlinePlayers();
		return onlinePlayers.stream().map(PlatformPlayer::name).collect(Collectors.toList());
	}

	public static final class PlayerParseException extends ParserException {
		private PlayerParseException(
				final @NonNull String input,
				final @NonNull CommandContext<?> context
		) {
			super(
					PlayerParser.class,
					context,
					TranslatableCaption.translatableCaption("argument.parse.failure.player"),
					CaptionVariable.of("input", input)
			);
		}
	}
}
