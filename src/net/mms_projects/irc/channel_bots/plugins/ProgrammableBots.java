package net.mms_projects.irc.channel_bots.plugins;

import net.mms_projects.irc.channel_bots.ChannelBots;
import net.mms_projects.irc.channel_bots.ChannelList;
import net.mms_projects.irc.channel_bots.Plugin;
import net.mms_projects.irc.channel_bots.ServerList;
import net.mms_projects.irc.channel_bots.Socket;
import net.mms_projects.irc.channel_bots.UserList;
import net.mms_projects.irc.channel_bots.irc.Handler;
import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;

public class ProgrammableBots extends Plugin {

	public ProgrammableBots(Socket socket, Handler handler, UserList userList,
			ChannelList channelList, ServerList serverList) {
		super(socket, handler, userList, channelList, serverList);
		
		NickIntroduce pBot = new NickIntroduce();
		pBot.nickname = "PBot";
		pBot.hobs = 1;
		pBot.timestamp = (int) (ChannelBots.date.getDate().getTime() / 1000);
		pBot.username = "P-Bot";
		pBot.hostname = "channel-bot.mms-projects.net";
		pBot.server = "channels.mms-projects.net";
		pBot.serviceStamp = 1;
		pBot.realname = "Programmable Bot interface";
		socket.write(pBot.toString());
	}

}
