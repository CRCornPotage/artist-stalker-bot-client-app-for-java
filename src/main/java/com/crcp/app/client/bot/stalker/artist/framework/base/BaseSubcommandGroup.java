package com.crcp.app.client.bot.stalker.artist.framework.base;

import java.util.List;

import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

public abstract class BaseSubcommandGroup {

	@Getter
	private final SubcommandGroupData subcommandGroupData;
	
	@Getter
	private final List<BaseSubcommand> subCommands;

	protected BaseSubcommandGroup(
			SubcommandGroupData subcommandGroupData, 
			List<BaseSubcommand> subCommands
	) {
		this.subcommandGroupData = subcommandGroupData;
		this.subCommands = subCommands;
	}
	
}
