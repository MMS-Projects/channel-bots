package net.mms_projects.irc.channel_bots.plugins;

import net.mms_projects.irc.channel_bots.Bot;
import net.mms_projects.irc.channel_bots.ChannelList;
import net.mms_projects.irc.channel_bots.Plugin;
import net.mms_projects.irc.channel_bots.ServerList;
import net.mms_projects.irc.channel_bots.Socket;
import net.mms_projects.irc.channel_bots.UserList;
import net.mms_projects.irc.channel_bots.irc.Handler;
import net.mms_projects.irc.channel_bots.irc.commands.Notice;
import net.mms_projects.irc.channel_bots.irc.commands.PrivMsg;
import net.mms_projects.irc.channel_bots.listeners.MessageListener;
import net.mms_projects.irc.channel_bots.managers.ServiceManager;
import net.mms_projects.irc.channel_bots.pbl.Parser;

public class ProgrammableBots extends Plugin implements MessageListener {
	public ServiceManager manager;
	public Bot pbot;
	public Parser pblParser = new Parser();
	
	public ProgrammableBots(Socket socket, Handler handler, UserList userList,
			ChannelList channelList, ServerList serverList) {
		super(socket, handler, userList, channelList, serverList);
		
		this.manager = new ServiceManager(socket, handler, userList, channelList, serverList);
		handler.addManager(this.manager);
		handler.addMessageListener(this);
		
		pbot = new Bot("PBot", "Programmable Bot interface", "P-Bot",
				"channel-bot.mms-projects.net");
		this.manager.newBot(pbot);
		pbot.join("#test");
	}

	public void tick () {
		super.tick();
	}

	@Override
	public void onPrivMsg(PrivMsg event) {
		if (event.target.equalsIgnoreCase("#test")) {
			this.pbot.privMsg(event.target, this.pblParser.parse(event.text));
		}
	}

	@Override
	public void onNotice(Notice event) {
		// TODO Auto-generated method stub
		
	}
	
}
