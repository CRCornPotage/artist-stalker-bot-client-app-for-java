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
	private final List<BaseSubCommand> subCommands;
	
	@Getter
	private final List<OptionData> options;
	
	protected BaseSlashCommand(
			SlashCommandData slashCommandData
	) {
		this(slashCommandData, Optional.empty(), Optional.empty());
	}
	
	protected BaseSlashCommand(
			SlashCommandData slashCommandData,
			SubCommandsHolder holder
	) {
		this(slashCommandData, Optional.of(holder), Optional.empty());
	}
	
	protected BaseSlashCommand(
			SlashCommandData slashCommandData,
			OptionsHolder holder
	) {
		this(slashCommandData, Optional.empty(), Optional.of(holder));
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
			Optional<SubCommandsHolder> subCommandsHolder,
			Optional<OptionsHolder> optionHolder
	) {
		this.slashCommandData = slashCommandData;
		this.subCommands = subCommandsHolder
				.orElse(new SubCommandsHolder(List.of()))
				.subCommands;
		this.options = optionHolder
				.orElse(new OptionsHolder(List.of()))
				.options;
	}
	
	protected record SubCommandsHolder(List<BaseSubCommand> subCommands) {}
	protected record OptionsHolder(List<OptionData> options) {}
	
}
