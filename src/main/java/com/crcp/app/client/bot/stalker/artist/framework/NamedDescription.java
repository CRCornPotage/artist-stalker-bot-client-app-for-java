package com.crcp.app.client.bot.stalker.artist.framework;

public record NamedDescription(
		String name,
		String description
) {
	public NamedDescription() {
		this("", "");
	}
}
