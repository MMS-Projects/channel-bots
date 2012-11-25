package net.mms_projects.irc.channel_bots;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.mms_projects.irc.channel_bots.irc.Command;
import net.mms_projects.irc.channel_bots.irc.Handler;
import net.mms_projects.irc.channel_bots.irc.Parser;
import net.mms_projects.irc.channel_bots.irc.commands.EOS;
import net.mms_projects.irc.channel_bots.irc.commands.NetInfo;
import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.Pass;
import net.mms_projects.irc.channel_bots.irc.commands.Ping;
import net.mms_projects.irc.channel_bots.irc.commands.SetHost;
import net.mms_projects.irc.channel_bots.irc.commands.Away;
import net.mms_projects.irc.channel_bots.plugins.Main;

public class ChannelBots {

	private BlockingQueue<Command> parsed = new LinkedBlockingQueue<Command>();
	
	public void run() {
		final Handler handler = new Handler();
		final UserList userList = new UserList();
		
		Pass pass = new Pass();
		pass.password = "PassWord";
		
		NetInfo netInfo = new NetInfo();
		netInfo.maxGlobal = 10;
		netInfo.currentTime = (int) (System.currentTimeMillis() / 1000);
		netInfo.protocolVersion = 0;
		netInfo.cloakHash = "*";
		netInfo.networkName = "MMS-Projects IRC";
		
		final Socket socket = new Socket();
		socket.write(pass.toString());
		socket.write("SERVER channels.mms-projects.net 1 :Channels services");
		socket.write("NICK ChannelBot 1 1 ChannelBot channel-bot.mms-projects.net channels.mms-projects.net 1 :Channel Bot");
		socket.write(netInfo.toString());
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Parser parser = new Parser();
				parser.addCommand(new Pass());
				parser.addCommand(new EOS());
				parser.addCommand(new NetInfo());
				parser.addCommand(new Ping());
				parser.addCommand(new NickIntroduce());
				parser.addCommand(new SetHost());
				parser.addCommand(new Away());
				
				while (true) {
					String line = socket.read();
					Command command = parser.parse(line);
					
					if (command != null) {
						ChannelBots.this.parsed.add(command);
					}
				}
			}
		}).start();
		
		final Main main = new Main(socket, handler, userList);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Command command = null;
					try {
						command = ChannelBots.this.parsed.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.handle(command);
				}
			}
		}).start();
	}
	
	static public void main(String[] args) {
		ChannelBots channelBots = new ChannelBots();
		channelBots.run();
	}

}
