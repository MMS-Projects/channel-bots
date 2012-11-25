package net.mms_projects.irc.channel_bots;

import net.mms_projects.irc.channel_bots.irc.Handler;

public class Plugin {

	protected Socket socket;
	protected Handler handler;
	
	public Plugin(Socket socket, Handler handler) {
		this.socket = socket;
		this.handler = handler;
	}
	
}
