package net.mms_projects.irc.channel_bots.managers;

import net.mms_projects.irc.channel_bots.Bot;
import net.mms_projects.irc.channel_bots.BotList;
import net.mms_projects.irc.channel_bots.ChannelList;
import net.mms_projects.irc.channel_bots.Manager;
import net.mms_projects.irc.channel_bots.ServerList;
import net.mms_projects.irc.channel_bots.Socket;
import net.mms_projects.irc.channel_bots.UserList;
import net.mms_projects.irc.channel_bots.irc.Handler;

public class ServiceManager extends Manager {
	public BotList bots;

	public ServiceManager(Socket socket, Handler handler, UserList userList,
			ChannelList channelList, ServerList serverList) {
		super(socket, handler, userList, channelList, serverList);
		bots = new BotList();
	}

	@Override
	public void tick() {

	}

	public void newBot(Bot bot) {
		bots.add(bot);
		write(bot.getNickIntroduce());
	}
}
