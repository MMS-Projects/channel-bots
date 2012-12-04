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
	public net.mms_projects.irc.channel_bots.pbl.Handler pblHandler;
	public Parser pblParser;
	
	public ProgrammableBots(Socket socket, Handler handler, UserList userList,
			ChannelList channelList, ServerList serverList) {
		super(socket, handler, userList, channelList, serverList);
		
		this.pblHandler = new net.mms_projects.irc.channel_bots.pbl.Handler();
		this.pblParser = new Parser(this.pblHandler);
		
		this.manager = new ServiceManager(socket, handler, userList, channelList, serverList);
		handler.addManager(this.manager);
		handler.addMessageListener(this);
		
		pbot = new Bot("PBot", "Programmable Bot interface", "P-Bot",
				"channel-bot.mms-projects.net");
		
		this.pblHandler.setVariable("internal.irc.me", pbot.nickname);
		this.manager.newBot(pbot);
		pbot.join("#test");
	}

	public void tick () {
		super.tick();
	}

	@Override
	public void onPrivMsg(PrivMsg event) {
		if (event.target.equalsIgnoreCase("#test")) {
			this.pblHandler.setVariable("internal.irc.chan", event.target);
			this.pblHandler.setVariable("internal.irc.nick", event.source);
			
			this.pbot.privMsg(event.target, this.pblParser.eval(event.text));
		}
	}

	@Override
	public void onNotice(Notice event) {
		// TODO Auto-generated method stub
		
	}
	
}
