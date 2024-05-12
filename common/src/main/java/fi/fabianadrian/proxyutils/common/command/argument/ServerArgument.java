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
import fi.fabianadrian.proxyutils.common.platform.PlatformServer;
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
 * Argument parse for {@link PlatformServer servers}
 *
 * @param <C> Command sender type
 */
public final class ServerArgument<C> extends CommandArgument<C, PlatformServer> {

	private ServerArgument(final boolean required, final @NotNull String name, final @NotNull String defaultValue, final @Nullable BiFunction<CommandContext<C>, String, List<String>> suggestionsProvider, final @NotNull ArgumentDescription defaultDescription, final @NotNull Collection<@NotNull BiFunction<@NotNull CommandContext<C>, @NotNull Queue<@NotNull String>, @NotNull ArgumentParseResult<Boolean>>> argumentPreprocessors) {
		super(required, name, new ServerParser<>(), defaultValue, TypeToken.get(PlatformServer.class), suggestionsProvider, defaultDescription, argumentPreprocessors);
	}

	/**
	 * Create a new argument builder
	 *
	 * @param name Argument name
	 * @param <C>  Command sender type
	 * @return Constructed builder
	 */
	public static <C> CommandArgument.@NotNull Builder<C, PlatformServer> newBuilder(final @NotNull String name) {
		return new Builder<C>(name).withParser(new ServerParser<>());
	}

	/**
	 * Create a new required server argument
	 *
	 * @param name Argument name
	 * @param <C>  Command sender type
	 * @return Created argument
	 */
	public static <C> @NotNull CommandArgument<C, PlatformServer> of(final @NotNull String name) {
		return ServerArgument.<C>newBuilder(name).asRequired().build();
	}

	/**
	 * Create a new optional server argument
	 *
	 * @param name Argument name
	 * @param <C>  Command sender type
	 * @return Created argument
	 */
	public static <C> @NotNull CommandArgument<C, PlatformServer> optional(final @NotNull String name) {
		return ServerArgument.<C>newBuilder(name).asOptional().build();
	}

	public static final class Builder<C> extends CommandArgument.Builder<C, PlatformServer> {

		private Builder(final @NotNull String name) {
			super(TypeToken.get(PlatformServer.class), name);
		}

		@Override
		public @NotNull CommandArgument<@NotNull C, @NotNull PlatformServer> build() {
			return new ServerArgument<>(this.isRequired(), this.getName(), this.getDefaultValue(), this.getSuggestionsProvider(), this.getDefaultDescription(), new LinkedList<>());
		}

	}

	public static final class ServerParser<C> implements ArgumentParser<C, PlatformServer> {
		@Override
		public @NotNull ArgumentParseResult<@NotNull PlatformServer> parse(final @NotNull CommandContext<@NotNull C> ctx, final @NotNull Queue<@NotNull String> inputQueue) {
			final String input = inputQueue.peek();
			if (input == null) {
				return ArgumentParseResult.failure(new NoInputProvidedException(ServerParser.class, ctx));
			}

			final List<PlatformServer> onlinePlayers = ctx.get(ProxyUtilsContextKeys.PLATFORM_KEY).servers();

			for (PlatformServer player : onlinePlayers) {
				if (player.name().equals(input)) {
					inputQueue.remove();
					return ArgumentParseResult.success(player);
				}
			}
			return ArgumentParseResult.failure(new ServerParseException(input, ctx));
		}

		@Override
		public @NotNull List<@NotNull String> suggestions(final @NotNull CommandContext<C> ctx, final @NotNull String input) {
			final List<PlatformServer> onlinePlayers = ctx.get(ProxyUtilsContextKeys.PLATFORM_KEY).servers();
			return onlinePlayers.stream().map(PlatformServer::name).collect(Collectors.toList());
		}
	}

	public static final class ServerParseException extends ParserException {
		private static final long serialVersionUID = -7635049156069168690L;

		private ServerParseException(final @NotNull String input, final @NotNull CommandContext<?> context) {
			super(ServerParser.class, context, Caption.of("argument.parse.failure.player"), CaptionVariable.of("input", input));
		}
	}
}
