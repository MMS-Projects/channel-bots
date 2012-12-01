package net.mms_projects.irc.channel_bots;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

import net.mms_projects.irc.channel_bots.irc.commands.Join;

public class Channel {

	public String name;
	public Topic topic;

	static public Collection<Channel> createFromJoin(Join event) {
		CopyOnWriteArrayList<Channel> channels = new CopyOnWriteArrayList<Channel>();
		for (String name : event.channels) {
			Channel channel = new Channel();
			channel.name = name;
			channels.add(channel);
		}
		return channels;
	}

}
