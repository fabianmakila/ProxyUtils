package fi.fabianadrian.proxyutils.common.command.argument;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.arguments.CommandArgument;
import cloud.commandframework.arguments.parser.ArgumentParseResult;
import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.captions.Caption;
import cloud.commandframework.captions.CaptionVariable;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.exceptions.parsing.NoInputProvidedException;
import cloud.commandframework.exceptions.parsing.ParserException;
import fi.fabianadrian.proxyutils.common.command.ProxyUtilsContextKeys;
import fi.fabianadrian.proxyutils.common.platform.PlatformPlayer;
import io.leangen.geantyref.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Argument parse for {@link PlatformPlayer players}
 *
 * @param <C> Command sender type
 */
public final class PlayerArgument<C> extends CommandArgument<C, PlatformPlayer> {

	private PlayerArgument(final boolean required, final @NotNull String name, final @NotNull String defaultValue, final @Nullable BiFunction<CommandContext<C>, String, List<String>> suggestionsProvider, final @NotNull ArgumentDescription defaultDescription, final @NotNull Collection<@NotNull BiFunction<@NotNull CommandContext<C>, @NotNull Queue<@NotNull String>, @NotNull ArgumentParseResult<Boolean>>> argumentPreprocessors) {
		super(required, name, new PlayerParser<>(), defaultValue, TypeToken.get(PlatformPlayer.class), suggestionsProvider, defaultDescription, argumentPreprocessors);
	}

	/**
	 * Create a new argument builder
	 *
	 * @param name Argument name
	 * @param <C>  Command sender type
	 * @return Constructed builder
	 */
	public static <C> CommandArgument.@NotNull Builder<C, PlatformPlayer> newBuilder(final @NotNull String name) {
		return new Builder<C>(name).withParser(new PlayerParser<>());
	}

	/**
	 * Create a new required server argument
	 *
	 * @param name Argument name
	 * @param <C>  Command sender type
	 * @return Created argument
	 */
	public static <C> @NotNull CommandArgument<C, PlatformPlayer> of(final @NotNull String name) {
		return PlayerArgument.<C>newBuilder(name).asRequired().build();
	}

	/**
	 * Create a new optional server argument
	 *
	 * @param name Argument name
	 * @param <C>  Command sender type
	 * @return Created argument
	 */
	public static <C> @NotNull CommandArgument<C, PlatformPlayer> optional(final @NotNull String name) {
		return PlayerArgument.<C>newBuilder(name).asOptional().build();
	}

	public static final class Builder<C> extends CommandArgument.Builder<C, PlatformPlayer> {

		private Builder(final @NotNull String name) {
			super(TypeToken.get(PlatformPlayer.class), name);
		}

		@Override
		public @NotNull CommandArgument<@NotNull C, @NotNull PlatformPlayer> build() {
			return new PlayerArgument<>(this.isRequired(), this.getName(), this.getDefaultValue(), this.getSuggestionsProvider(), this.getDefaultDescription(), new LinkedList<>());
		}

	}

	public static final class PlayerParser<C> implements ArgumentParser<C, PlatformPlayer> {
		@Override
		public @NotNull ArgumentParseResult<@NotNull PlatformPlayer> parse(final @NotNull CommandContext<@NotNull C> ctx, final @NotNull Queue<@NotNull String> inputQueue) {
			final String input = inputQueue.peek();
			if (input == null) {
				return ArgumentParseResult.failure(new NoInputProvidedException(PlayerParser.class, ctx));
			}

			final List<PlatformPlayer> onlinePlayers = ctx.get(ProxyUtilsContextKeys.PLATFORM_KEY).onlinePlayers();

			for (PlatformPlayer player : onlinePlayers) {
				if (player.name().equals(input)) {
					inputQueue.remove();
					return ArgumentParseResult.success(player);
				}
			}
			return ArgumentParseResult.failure(new PlayerParseException(input, ctx));
		}

		@Override
		public @NotNull List<@NotNull String> suggestions(final @NotNull CommandContext<C> ctx, final @NotNull String input) {
			final List<PlatformPlayer> onlinePlayers = ctx.get(ProxyUtilsContextKeys.PLATFORM_KEY).onlinePlayers();
			return onlinePlayers.stream().map(PlatformPlayer::name).collect(Collectors.toList());
		}
	}

	public static final class PlayerParseException extends ParserException {
		private static final long serialVersionUID = -8327133003700188401L;

		private PlayerParseException(final @NotNull String input, final @NotNull CommandContext<?> context) {
			super(PlayerParser.class, context, Caption.of("argument.parse.failure.player"), CaptionVariable.of("input", input));
		}
	}
}