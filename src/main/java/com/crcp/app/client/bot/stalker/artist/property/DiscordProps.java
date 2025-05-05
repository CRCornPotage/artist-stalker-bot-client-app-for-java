package com.crcp.app.client.bot.stalker.artist.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.crcp.app.client.bot.stalker.artist.constant.AppConst;
import com.crcp.app.client.bot.stalker.artist.constant.PropsConst;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = PropsConst.DISCORD_PREFIX)
@Getter
@Setter
public class DiscordProps {
	
	private BotProps bot;
	
	@Getter
	@Setter
	public class BotProps {
		
		private String token = AppConst.UNKNOWN;
		
	}
	
}
