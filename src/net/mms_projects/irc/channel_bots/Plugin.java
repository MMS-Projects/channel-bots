package net.mms_projects.irc.channel_bots;

import net.mms_projects.irc.channel_bots.irc.Handler;

public class Plugin {

	protected Socket socket;
	protected Handler handler;
	protected UserList userList;
	protected ChannelList channelList;
	protected ServerList serverList;

	public Plugin(Socket socket, Handler handler, UserList userList,
			ChannelList channelList, ServerList serverList) {
		handler.addPlugin(this);
		this.socket = socket;
		this.handler = handler;
		this.userList = userList;
		this.channelList = channelList;
		this.serverList = serverList;
	}

	public void tick() {}
}
