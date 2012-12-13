package net.mms_projects.irc.channel_bots.pb;

import net.mms_projects.irc.channel_bots.Bot;
import net.mms_projects.irc.channel_bots.ChannelList;
import net.mms_projects.irc.channel_bots.ServerList;
import net.mms_projects.irc.channel_bots.UserList;
import net.mms_projects.irc.channel_bots.irc.commands.PrivMsg;
import net.mms_projects.irc.channel_bots.pbl.Handler;

public class PassedData {

	public UserList userList;
	public ChannelList channelList;
	public ServerList serverList;
	public Bot bot;
	public PrivMsg event;
	public Handler pblHandler;

	public PassedData(UserList userList, ChannelList channelList,
			ServerList serverList, Bot bot, PrivMsg event, Handler pblHandler) {
		this.userList = userList;
		this.channelList = channelList;
		this.serverList = serverList;
		this.bot = bot;
		this.event = event;
		this.pblHandler = pblHandler;
	}

}
