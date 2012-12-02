package net.mms_projects.irc.channel_bots.plugins;

import net.mms_projects.irc.channel_bots.Bot;
import net.mms_projects.irc.channel_bots.ChannelList;
import net.mms_projects.irc.channel_bots.Plugin;
import net.mms_projects.irc.channel_bots.ServerList;
import net.mms_projects.irc.channel_bots.Socket;
import net.mms_projects.irc.channel_bots.UserList;
import net.mms_projects.irc.channel_bots.irc.Handler;
import net.mms_projects.irc.channel_bots.managers.ServiceManager;

public class ProgrammableBots extends Plugin {
	public ServiceManager manager;
	public Bot pbot;
	
	public ProgrammableBots(Socket socket, Handler handler, UserList userList,
			ChannelList channelList, ServerList serverList) {
		super(socket, handler, userList, channelList, serverList);
		
		this.manager = new ServiceManager(socket, handler, userList, channelList, serverList);
		handler.addManager(this.manager);
		
		pbot = new Bot("PBot", "Programmable Bot interface", "P-Bot",
				"channel-bot.mms-projects.net");
		this.manager.newBot(pbot);
		pbot.join("#test");
	}

	public void tick () {
		super.tick();
		pbot.part("#test");
		pbot.join("#test");
	}
}
