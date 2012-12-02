package net.mms_projects.irc.channel_bots;

import net.mms_projects.irc.channel_bots.irc.Command;
import net.mms_projects.irc.channel_bots.irc.Handler;

public abstract class Manager {
	public Socket socket;
	public Handler handler;
	public UserList userList;
	public ChannelList channelList;
	public ServerList serverList;
	
	public Manager (Socket socket, Handler handler, UserList userList,
			ChannelList channelList, ServerList serverList) {
		this.socket = socket;
		this.handler = handler;
		this.userList = userList;
		this.channelList = channelList;
		this.serverList = serverList;
	}
	public abstract void tick();
	
	public void write (Command command) {
		write(command.toString());
	}
	public void write (String string) {
		socket.write(string);
	}
}
