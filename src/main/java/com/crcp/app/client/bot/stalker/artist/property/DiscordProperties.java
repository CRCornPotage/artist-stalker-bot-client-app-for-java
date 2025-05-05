package com.crcp.app.client.bot.stalker.artist.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "discord")
@Getter
@Setter
public class DiscordProperties {
	
	private BotProperties bot;
	
	@Getter
	@Setter
	public class BotProperties {
		
		private String token = "unkown";
		
	}
	
}
