package com.crcp.app.client.bot.stalker.artist.framework.base;

import java.util.List;

import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

public abstract class BaseSubcommand {
	
	@Getter 
	private final SubcommandData subcommandData;
	
	@Getter 
	private final List<OptionData> options;

	protected BaseSubcommand(
			SubcommandData subcommandData,
			List<OptionData> options
	) {
		this.subcommandData = subcommandData;
		this.options = options;
	}
	
	public void execute(SlashCommandInteraction interaction) {}
	
	public final boolean hasOptions() {
		return !this.options.isEmpty();
	}
	
}
