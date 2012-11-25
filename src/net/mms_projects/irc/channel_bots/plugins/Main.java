package net.mms_projects.irc.channel_bots.plugins;

import net.mms_projects.irc.channel_bots.Plugin;
import net.mms_projects.irc.channel_bots.Socket;
import net.mms_projects.irc.channel_bots.irc.Handler;
import net.mms_projects.irc.channel_bots.irc.commands.Ping;
import net.mms_projects.irc.channel_bots.listeners.PingPongListener;

public class Main extends Plugin implements PingPongListener {

	public Main(Socket socket, Handler handler) {
		super(socket, handler);
		
		handler.addPingPongListener(this);
	}

	@Override
	public void onPing(Ping ping) {
		socket.write("PONG :" + ping.token);
	}

}
