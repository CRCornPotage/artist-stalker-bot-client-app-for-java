package com.crcp.app.client.bot.stalker.artist.framework;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.crcp.app.client.bot.stalker.artist.property.DiscordProps;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Component
public final class BotInitializer implements CommandLineRunner {
	
	private final DiscordProps discordProperties;
	private final List<ListenerAdapter> listeners;
	private final CommandRegister commandRegister;
	
	public BotInitializer(
			DiscordProps discordProperties, 
			List<ListenerAdapter> listeners,
			CommandRegister commandRegister
	) {
		this.discordProperties = discordProperties;
		this.listeners = listeners;
		this.commandRegister = commandRegister;
	}
	
	@Override
	public void run(String... args) {
		JDA jda = JDABuilder
				.createDefault(
						this.discordProperties
							.getBot()
							.getToken()
				)
				.addEventListeners(this.listeners)
				.build();
		commandRegister
			.execute(jda);
	}
	
}
