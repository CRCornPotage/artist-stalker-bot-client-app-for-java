package com.crcp.app.client.bot.stalker.artist.framework.dispatcher;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.crcp.app.client.bot.stalker.artist.framework.base.BaseSlashCommand;
import com.crcp.app.client.bot.stalker.artist.framework.base.BaseSubcommand;
import com.crcp.app.client.bot.stalker.artist.framework.base.BaseSubcommandGroup;

import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

@Component
public class CommandDispatcher {
	
	private final List<BaseSlashCommand> slashCommands;
	
	public CommandDispatcher(
			List<BaseSlashCommand> slashCommands
	) {
		this.slashCommands = slashCommands;
	}
	
	public void execute(SlashCommandInteraction interaction) {
		final var maybeTargetSlashCommand = this.findSlashCommand(interaction);
		if (maybeTargetSlashCommand.isEmpty()) {
			return;
		}
		try {
			final var targetSlashCommand = maybeTargetSlashCommand.get();
			this.executeSlashCommandOrSubCommand(
					interaction,
					targetSlashCommand
			);
		} catch (Exception e) {
			// TODO エラー発生ログ(Error
			// TODO 処理以上終了ログ(Info
		}
		// TODO 処理正常終了ログ(Info
	}
	
	private Optional<BaseSlashCommand> findSlashCommand(
			SlashCommandInteraction interaction
	) {
		return this.slashCommands
				.stream()
				.filter(
						slashCommand -> Objects.equals(
								slashCommand
										.getSlashCommandData()
										.getName(), 
								interaction.getName()
						)
				)
				.findFirst();
	}
	
	private Optional<BaseSubcommandGroup> findSubCommandGroup(
			SlashCommandInteraction interaction,
			BaseSlashCommand slashCommand
	) {
		return slashCommand.getSubCommandGroups()
				.stream()
				.filter(
						subCommandGroup -> Objects
								.equals(
										subCommandGroup
												.getSubcommandGroupData()
												.getName(), 
										interaction.getSubcommandGroup()
								)
				)
				.findFirst();
	}
	
	private Optional<BaseSubcommand> findSubCommand(
			SlashCommandInteraction interaction, 
			BaseSubcommandGroup subcommandGroup
	) {
		return subcommandGroup.getSubCommands()
				.stream()
				.filter(
						subCommand -> Objects
								.equals(
										subCommand
												.getSubcommandData()
												.getName(), 
										interaction.getSubcommandName()
								)
				)
				.findFirst();
	}
	
	private Optional<BaseSubcommand> findSubCommand(
			SlashCommandInteraction interaction, 
			BaseSlashCommand slashCommand
	) {
		return slashCommand.getSubCommands()
				.stream()
				.filter(
						subCommand -> Objects
								.equals(
										subCommand
												.getSubcommandData()
												.getName(), 
										interaction.getSubcommandName()
								)
				)
				.findFirst();
	}
	
	private void executeSlashCommandOrSubCommand(
			SlashCommandInteraction interaction, 
			BaseSlashCommand slashCommand
	) {
		final var maybeTargetSubCommandGroup = this.findSubCommandGroup(
				interaction,
				slashCommand
		);
		final var maybeTargetSubCommand = maybeTargetSubCommandGroup.isPresent()
				? this.findSubCommand(interaction, maybeTargetSubCommandGroup.get())
				: this.findSubCommand(interaction, slashCommand);
		if (maybeTargetSubCommand.isPresent()) {
			// 処理開始ログ(Info
			maybeTargetSubCommand.get().execute(interaction);
		} else {
			// TODO 処理開始ログ(Info
			slashCommand.execute(interaction);
		}
		
	}
	
}
