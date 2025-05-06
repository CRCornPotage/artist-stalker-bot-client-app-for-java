package com.crcp.app.client.bot.stalker.artist.framework.base;

import net.dv8tion.jda.api.events.session.ReadyEvent;

public abstract class BaseOnReadyEventHandler {
	public abstract void execute(ReadyEvent event);
}
