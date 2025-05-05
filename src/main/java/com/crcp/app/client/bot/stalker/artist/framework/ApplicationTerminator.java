package com.crcp.app.client.bot.stalker.artist.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.crcp.app.client.bot.stalker.artist.code.ExitCode;

@Component
public class ApplicationTerminator {

    private final ApplicationContext context;

    public ApplicationTerminator(ApplicationContext context) {
        this.context = context;
    }

    public void terminate(ExitCode exitCode) {
    	// TODO 強制終了ログ(Fatal
        int code = SpringApplication.exit(context, () -> exitCode.getCode());
        System.exit(code);
    }
}