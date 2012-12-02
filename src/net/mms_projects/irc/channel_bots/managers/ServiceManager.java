package net.mms_projects.irc.channel_bots.managers;

import net.mms_projects.irc.channel_bots.Bot;
import net.mms_projects.irc.channel_bots.BotList;
import net.mms_projects.irc.channel_bots.Manager;

public class ServiceManager extends Manager {
	public BotList bots;
	
	public ServiceManager () {
		bots = new BotList();
	}
	@Override
	public void tick() {
		
	}
	
	public void newBot(Bot bot) {
		bots.add(bot);
	}
}
