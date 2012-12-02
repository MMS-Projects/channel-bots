package net.mms_projects.irc.channel_bots;

import net.mms_projects.irc.channel_bots.irc.Command;
import net.mms_projects.irc.channel_bots.irc.commands.Join;
import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.Quit;
import net.mms_projects.irc.channel_bots.managers.ServiceManager;

public class Bot {
	public String nickname;
	public String hostname;
	public String username;
	public String realname;
	public ServiceManager manager;
	public boolean quit;
	
	public Bot (String nickname, String realname, String username, 
			String hostname) {
		this.quit = false;
		this.nickname = nickname;
		this.hostname = hostname;
		this.username = username;
		this.realname = realname;
	}

	public NickIntroduce getNickIntroduce() {
		NickIntroduce c = new NickIntroduce();
		c.nickname = nickname;
		c.hobs = 1;
		c.timestamp = (int) (ChannelBots.date.getDate().getTime() / 1000);
		c.username = username;
		c.hostname = hostname;
		c.server = ChannelBots.server.server;
		c.serviceStamp = 1;
		c.realname = realname;
		return c;
	}
	
	public void write (Command command) {
		manager.write(command);
	}
	
	public void quit (String reason) {
		Quit c = new Quit();
		c.nickname = nickname;
		c.reason = reason;
		write(c);
		quit = true;
	}
	
	public void join (String channel) {
		Join c = new Join();
		c.channels = new String[]{channel};
		c.nickname = nickname;
		write(c);
	}

	public void say(String destination, String message) {
		//TODO Privmsg
	}
}
