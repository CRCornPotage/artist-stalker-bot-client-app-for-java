package com.crcp.app.client.bot.stalker.artist.framework;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.logging.log4j.util.Strings;

import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class BaseCommand {
	
	@Getter
	private final NamedDescription namedDescription;
	
	@Getter
	private final List<BaseSubCommand> subCommands;
	
	@Getter
	private final List<OptionData> options;
	
	private final List<Predicate<CommandInteraction>> mustExecuteConditions;
	
	protected BaseCommand(
			NamedDescription namedDescription
	) {
		this(namedDescription, Optional.empty(), Optional.empty());
	}
	
	protected BaseCommand(
			NamedDescription namedDescription,
			SubCommandsHolder holder
	) {
		this(namedDescription, Optional.of(holder), Optional.empty());
	}
	
	protected BaseCommand(
			NamedDescription namedDescription,
			OptionsHolder holder
	) {
		this(namedDescription, Optional.empty(), Optional.of(holder));
	}
	
	public final void execute(CommandInteraction interaction) {
		if (this.isNotThisCommand(interaction)) return;
		if (this.mustExecute(interaction)) {
			this.runMainLogic(interaction);
		} else {
			this.subCommands
				.parallelStream()
				.forEach(subCommand -> subCommand.execute(interaction));
		}
	}
	
	public final boolean hasSubCommand() {
		return !this.subCommands.isEmpty();
	}
	
	public final boolean hasOptions() {
		return !this.options.isEmpty();
	}
	
	protected List<Predicate<CommandInteraction>> createMustExecuteConditions() {
		return List.of(
			    i -> this.subCommands.isEmpty(),
			    i -> Strings.isEmpty(i.getSubcommandName())
		);
	}
	
	protected abstract void runMainLogic(CommandInteraction interaction);
	
	private boolean isNotThisCommand(
			CommandInteraction interaction
	) {
		return !Objects.equals(
				interaction.getName(), 
				this.namedDescription.name()
		);
	}
	
	private BaseCommand(
			NamedDescription namedDescription,
			Optional<SubCommandsHolder> subCommandsHolder,
			Optional<OptionsHolder> optionHolder
	) {
		this.namedDescription = namedDescription;
		this.subCommands = subCommandsHolder
				.orElse(new SubCommandsHolder(List.of()))
				.subCommands;
		this.options = optionHolder
				.orElse(new OptionsHolder(List.of()))
				.options;
		this.mustExecuteConditions = this.createMustExecuteConditions();
	}
	
	private boolean mustExecute(CommandInteraction interaction) {
		return this.mustExecuteConditions
				.stream()
				.anyMatch(cond -> cond.test(interaction));
	}
	
	protected record SubCommandsHolder(List<BaseSubCommand> subCommands) {}
	protected record OptionsHolder(List<OptionData> options) {}
	
}
