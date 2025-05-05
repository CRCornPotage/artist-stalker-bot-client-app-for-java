package com.crcp.app.client.bot.stalker.artist.framework;

import java.util.List;

import org.springframework.stereotype.Component;

import com.crcp.app.client.bot.stalker.artist.constant.PropsConst;
import com.crcp.app.client.bot.stalker.artist.property.SpringProps;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@Component
public class CommandRegister {
	
	private final SpringProps springProps;
	
	public CommandRegister(SpringProps springProps) {
		this.springProps = springProps;
	}
	
	public void execute(JDA jda) {
		switch (springProps.getProfiles().getActive()) {
			case PropsConst.PRE_ENV -> this.runPreLogic(jda);
			default -> this.runOtherLogic(jda);
		}
	}
	
	private void runPreLogic(JDA jda) {
		this.runOtherLogic(jda);
		// TODO 現行では少数のサーバでしか利用されていないためテストロジックを使用
		
	}
	
	private void runOtherLogic(JDA jda) {
		jda.getGuildCache().forEach(this::registerToGuild);
	}
	
	private void registerToGuild(Guild guild) {
		guild
				.updateCommands()
				.addCommands(this.createCommandDatas());
	}
	
	private List<CommandData> createCommandDatas() {
		
		return List.of(Commands.slash(null, null));
	}
	
}
