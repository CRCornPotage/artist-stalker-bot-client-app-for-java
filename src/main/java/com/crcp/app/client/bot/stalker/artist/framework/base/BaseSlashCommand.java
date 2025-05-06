package com.crcp.app.client.bot.stalker.artist.framework.base;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.logging.log4j.util.Strings;

import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public abstract class BaseSlashCommand {
	
	@Getter
	private final SlashCommandData slashCommandData;
	
	@Getter
	private final List<BaseSubcommand> subCommands;
	
	@Getter
	private final List<BaseSubcommandGroup> subCommandGroups;
	
	@Getter
	private final List<OptionData> options;
	
	protected BaseSlashCommand(
			SlashCommandData slashCommandData
	) {
		this(
				slashCommandData, 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty()
		);
	}
	
	protected BaseSlashCommand(
			SlashCommandData slashCommandData,
			SubcommandsHolder subCommandsHolder,
			SubcommandGroupsHolder subcommandGroupsHolder
	) {
		this(
				slashCommandData, 
				Optional.of(subCommandsHolder), 
				Optional.of(subcommandGroupsHolder),  
				Optional.empty()
		);
	}
	
	protected BaseSlashCommand(
			SlashCommandData slashCommandData,
			OptionsHolder holder
	) {
		this(
				slashCommandData, 
				Optional.empty(), 
				Optional.empty(), 
				Optional.of(holder)
		);
	}
	
	public void execute(SlashCommandInteraction interaction) {}
	
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
	
	private BaseSlashCommand(
			SlashCommandData slashCommandData,
			Optional<SubcommandsHolder> subCommandsHolder,
			Optional<SubcommandGroupsHolder> subcommandGroupsHolder,
			Optional<OptionsHolder> optionHolder
	) {
		this.slashCommandData = slashCommandData;
		this.subCommands = subCommandsHolder
				.orElse(new SubcommandsHolder(List.of()))
				.subCommands;
		this.subCommandGroups = subcommandGroupsHolder
				.orElse(new SubcommandGroupsHolder(List.of()))
				.subCommandGroups;
		this.options = optionHolder
				.orElse(new OptionsHolder(List.of()))
				.options;
	}
	
	protected record SubcommandsHolder(List<BaseSubcommand> subCommands) {}
	protected record SubcommandGroupsHolder(List<BaseSubcommandGroup> subCommandGroups) {}
	protected record OptionsHolder(List<OptionData> options) {}
	
}
