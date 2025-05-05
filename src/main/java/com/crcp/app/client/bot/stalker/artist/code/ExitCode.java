package com.crcp.app.client.bot.stalker.artist.code;

public enum ExitCode {
	
	SUCCESS(0),
	ERROR(1);
	
	private final int code;

	ExitCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}
}