package net.mms_projects.irc.channel_bots;

import net.mms_projects.irc.channel_bots.irc.commands.Join;

public class Channel {

	public String name;
	
	static public Channel createFromJoin(Join event) {
		Channel channel = new Channel();
		channel.name = event.channel;
		return channel;
	}
	
}
