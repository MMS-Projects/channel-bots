package net.mms_projects.irc.channel_bots.plugins;

import net.mms_projects.irc.channel_bots.Plugin;
import net.mms_projects.irc.channel_bots.Socket;
import net.mms_projects.irc.channel_bots.irc.Handler;
import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.Ping;
import net.mms_projects.irc.channel_bots.listeners.NickIntroduceListener;
import net.mms_projects.irc.channel_bots.listeners.PingPongListener;

public class Main extends Plugin implements PingPongListener, NickIntroduceListener {

	public Main(Socket socket, Handler handler) {
		super(socket, handler);
		
		handler.addPingPongListener(this);
		handler.addNickIntroduceListener(this);
	}

	@Override
	public void onPing(Ping ping) {
		socket.write("PONG :" + ping.token);
	}

	@Override
	public void onNickIntroduced(NickIntroduce introduce) {
		System.out.println("Hi " + introduce.nickname + " :D");
	}

}
