package net.mms_projects.irc.channel_bots.listeners;

import net.mms_projects.irc.channel_bots.irc.commands.Join;
import net.mms_projects.irc.channel_bots.irc.commands.Part;
import net.mms_projects.irc.channel_bots.irc.commands.Topic;

public interface ChannelListener {

	public void userJoined(Join event);
	public void userLeft(Part event);
	public void topicChanged(Topic event);
	
}
