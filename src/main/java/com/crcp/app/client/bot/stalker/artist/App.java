package com.crcp.app.client.bot.stalker.artist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.crcp.app.client.bot.stalker.artist.property.DiscordProps;

@SpringBootApplication
@EnableConfigurationProperties({
	DiscordProps.class
})
public final class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
