package com.crcp.app.client.bot.stalker.artist.framework;

import java.util.List;
import java.util.Objects;

import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class BaseSubCommand {
	
	@Getter 
	private final NamedDescription namedDescription;
	
	@Getter 
	private final List<OptionData> options;

	protected BaseSubCommand(
			NamedDescription namedDescription,
			List<OptionData> options
	) {
		this.namedDescription = namedDescription;
		this.options = options;
	}
	
	public final void execute(CommandInteraction interaction) {
		if (this.isNotThisSubCommand(interaction)) return;
		this.runMainLogic(interaction);
	}
	
	public final boolean hasOptions() {
		return !this.options.isEmpty();
	}
	
	protected abstract void runMainLogic(CommandInteraction interaction);
	
	private boolean isNotThisSubCommand(
			CommandInteraction interaction
	) {
		return !Objects.equals(
				interaction.getSubcommandName(), 
				this.namedDescription.name()
		);
	}
	
}
