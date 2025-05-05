package com.crcp.app.client.bot.stalker.artist.framework;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.crcp.app.client.bot.stalker.artist.code.ExitCode;
import com.crcp.app.client.bot.stalker.artist.property.DiscordProps;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Component
public class BotInitializer implements CommandLineRunner {
	
	private final DiscordProps discordProperties;
	private final List<ListenerAdapter> listeners;
	private final ApplicationTerminator applicationTerminator;
	
	public BotInitializer(
			DiscordProps discordProperties, 
			List<ListenerAdapter> listeners,
			ApplicationTerminator applicationTerminator
	) {
		this.discordProperties = discordProperties;
		this.listeners = listeners;
		this.applicationTerminator = applicationTerminator;
	}
	
	@Override
	public void run(String... args) {
		// TODO ログインログ(Info
		JDA jda = JDABuilder
				.createDefault(
						this.discordProperties
							.getBot()
							.getToken()
				)
				.addEventListeners(this.listeners)
				.build();
		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
			// TODO ログイン失敗ログ(Error
			// TODO ログイン異常終了ログ(Info
			this.applicationTerminator.terminate(ExitCode.ERROR);
		}
		// TODO ログイン正常終了ログ(Info
	}
	
}
