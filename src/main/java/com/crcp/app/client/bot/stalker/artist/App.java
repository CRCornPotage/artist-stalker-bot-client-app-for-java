package com.crcp.app.client.bot.stalker.artist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.crcp.app.client.bot.stalker.artist.property.DiscordProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	DiscordProperties.class
})
public final class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
