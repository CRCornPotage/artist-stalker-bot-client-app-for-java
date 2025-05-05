package com.crcp.app.client.bot.stalker.artist.framework.runner;

import java.util.List;

import org.springframework.stereotype.Component;

import com.crcp.app.client.bot.stalker.artist.framework.base.BaseOnReadyEventHandler;

import net.dv8tion.jda.api.events.session.ReadyEvent;

@Component
public class OnReadyEventHandlersRunner {
	
	private final List<BaseOnReadyEventHandler> onReadyEventHandlers;

	public OnReadyEventHandlersRunner(
			List<BaseOnReadyEventHandler> onReadyEventHandlers
	) {
		this.onReadyEventHandlers = onReadyEventHandlers;
	}
	
	public void execute(ReadyEvent event) {
		for (final var onReadyEventHandler : this.onReadyEventHandlers) {
			// TODO 処理開始ログ(Info
			try {
				onReadyEventHandler.execute(event);
			} catch (Exception e) {
				// TODO エラー発生ログ(Error
				// TODO 処理異常終了ログ(Info
				throw e;
			}
			// TODO 処理正常終了ログ(Info
		}
	}
	
}
