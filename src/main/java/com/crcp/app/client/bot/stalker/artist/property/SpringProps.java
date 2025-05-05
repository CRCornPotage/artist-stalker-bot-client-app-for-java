package com.crcp.app.client.bot.stalker.artist.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.crcp.app.client.bot.stalker.artist.constant.AppConst;
import com.crcp.app.client.bot.stalker.artist.constant.PropsConst;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = PropsConst.SPRING_PREFIX)
@Getter
@Setter
public class SpringProps {
	
	private ProfilesProps profiles;
	private ApplicationProps application;
	
	@Getter
	@Setter
	public class ProfilesProps {
		
		private String active = PropsConst.DEV_ENV;
		
	}
	
	@Getter
	@Setter
	public class ApplicationProps {
		
		private String name = AppConst.APP_NAME;
		
	}
	
}
