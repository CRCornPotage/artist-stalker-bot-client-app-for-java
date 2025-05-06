package com.crcp.app.client.bot.stalker.artist.framework.event.ready;

import java.util.List;

import org.springframework.stereotype.Component;

import com.crcp.app.client.bot.stalker.artist.constant.PropsConst;
import com.crcp.app.client.bot.stalker.artist.framework.base.BaseOnReadyEventHandler;
import com.crcp.app.client.bot.stalker.artist.framework.base.BaseSlashCommand;
import com.crcp.app.client.bot.stalker.artist.framework.base.BaseSubcommand;
import com.crcp.app.client.bot.stalker.artist.framework.base.BaseSubcommandGroup;
import com.crcp.app.client.bot.stalker.artist.property.SpringProps;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

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
		if (slashCommand.hasOptions()) {
			slashCommandData.addOptions(slashCommand.getOptions());
			return slashCommandData;
		}
		
		if (slashCommand.hasSubCommand()) {
			slashCommandData.addSubcommands(
					slashCommand
							.getSubcommands()
							.stream()
							.map(this::getSubcommandData)
							.toList()
			);
		}
		
		if (slashCommand.hasSubCommandGroup()) {
			slashCommandData.addSubcommandGroups(
					slashCommand
							.getSubcommandGroups()
							.stream()
							.map(this::getSubcommandGroupData)
							.toList()
			);
		}

		return slashCommandData;
	}
	
	private SubcommandGroupData getSubcommandGroupData(
			BaseSubcommandGroup subcommandGroup
	) {
		final var subcommandGroupData = subcommandGroup.getSubcommandGroupData();
		if (subcommandGroup.hasSubcommand()) {
			subcommandGroupData.addSubcommands(
					subcommandGroup
							.getSubcommands()
							.stream()
							.map(this::getSubcommandData)
							.toList()
			);
		}
		return subcommandGroupData;
	}
	
	private SubcommandData getSubcommandData(
			BaseSubcommand subcommand
	) {
		final var subcommandData = subcommand.getSubcommandData();
		if (subcommand.hasOptions()) {
			subcommandData.addOptions(subcommand.getOptions());
		}
		return subcommandData;
	}
	
}
