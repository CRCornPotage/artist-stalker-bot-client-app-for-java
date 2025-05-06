package com.crcp.app.client.bot.stalker.artist.framework;

import java.util.List;

import org.springframework.stereotype.Component;

import com.crcp.app.client.bot.stalker.artist.constant.PropsConst;
import com.crcp.app.client.bot.stalker.artist.framework.base.BaseOnReadyEventHandler;
import com.crcp.app.client.bot.stalker.artist.framework.base.BaseSlashCommand;
import com.crcp.app.client.bot.stalker.artist.framework.base.BaseSubcommand;
import com.crcp.app.client.bot.stalker.artist.property.SpringProps;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

@Component
public class SlashCommandRegister extends BaseOnReadyEventHandler {
	
	private final SpringProps springProps;
	private final List<BaseSlashCommand> slashCommands;
	
	public SlashCommandRegister(
			SpringProps springProps,
			List<BaseSlashCommand> slashCommands
	) {
		this.springProps = springProps;
		this.slashCommands = slashCommands;
	}
	
	@Override
	public void execute(ReadyEvent event) {
		JDA jda = event.getJDA();
		switch (springProps.getProfiles().getActive()) {
			case PropsConst.PRE_ENV -> this.runPreLogic(jda);
			default -> this.runOtherLogic(jda);
		}
	}
	
	private void runPreLogic(JDA jda) {
		jda
				.updateCommands()
				.addCommands(this.getCommandDatas());
	}
	
	private void runOtherLogic(JDA jda) {
		jda.getGuildCache().forEach(this::registerToGuild);
	}
	
	private void registerToGuild(Guild guild) {
		guild
				.updateCommands()
				.addCommands(this.getCommandDatas());
	}
	
	private List<SlashCommandData> getCommandDatas() {
		return this.slashCommands
				.stream()
				.map(this::getSlashCommandData)
				.toList();
	}
	
	private SlashCommandData getSlashCommandData(
			BaseSlashCommand slashCommand
	) {
		final var slashCommandData = slashCommand.getSlashCommandData();
		if (slashCommand.hasSubCommand()) {
			slashCommandData.addSubcommands(
					slashCommand
							.getSubCommands()
							.stream()
							.map(this::getSubCommandData)
							.toList()
			);
		} else if (slashCommand.hasOptions()) {
			slashCommandData.addOptions(slashCommand.getOptions());
		}
		return slashCommandData;
	}
	
	private SubcommandData getSubCommandData(
			BaseSubcommand subCommand
	) {
		final var subCommandData = subCommand.getSubcommandData();
		if (subCommand.hasOptions()) {
			subCommandData.addOptions(subCommand.getOptions());
		}
		return subCommandData;
	}
}
