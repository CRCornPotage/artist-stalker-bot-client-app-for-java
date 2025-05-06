package com.crcp.app.client.bot.stalker.artist.framework.base;

import java.util.List;

import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

public abstract class BaseSubcommandGroup {

	@Getter
	private final SubcommandGroupData subcommandGroupData;
	
	@Getter
	private final List<BaseSubcommand> subcommands;

	protected BaseSubcommandGroup(
			SubcommandGroupData subcommandGroupData, 
			List<BaseSubcommand> subcommands
	) {
		this.subcommandGroupData = subcommandGroupData;
		this.subcommands = subcommands;
	}
	
	public final boolean hasSubcommand() {
		return !this.subcommands.isEmpty();
	}
	
}
