package com.crcp.app.client.bot.stalker.artist.framework;

import org.springframework.stereotype.Component;

import com.crcp.app.client.bot.stalker.artist.code.ExitCode;
import com.crcp.app.client.bot.stalker.artist.framework.dispatcher.CommandDispatcher;
import com.crcp.app.client.bot.stalker.artist.framework.runner.OnReadyEventHandlersRunner;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Component
public class DiscordEventListener extends ListenerAdapter {
	
	private final CommandDispatcher commandDispatcher;
	private final OnReadyEventHandlersRunner onReadyEventHandlersRunner;
	private final ApplicationTerminator applicationTerminator;
	
	public DiscordEventListener(
			CommandDispatcher commandDispatcher,
			OnReadyEventHandlersRunner onReadyEventHandlersRunner,
			ApplicationTerminator applicationTerminator
	) {
		this.commandDispatcher = commandDispatcher;
		this.onReadyEventHandlersRunner = onReadyEventHandlersRunner;
		this.applicationTerminator = applicationTerminator;
	}
	
	@Override
	public void onReady(ReadyEvent event) {
		// TODO 開始ログ(Info
		try {
			this.onReadyEventHandlersRunner.execute(event);
		} catch (Exception e) {
			this.applicationTerminator.terminate(ExitCode.ERROR);
		}
		// TODO 終了ログ(Info
	}
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		// TODO 開始ログ(Info
		commandDispatcher.execute(event);
		// TODO 終了ログ(Info
	}
	
}
