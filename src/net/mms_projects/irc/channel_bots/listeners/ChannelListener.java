package net.mms_projects.irc.channel_bots.listeners;

import net.mms_projects.irc.channel_bots.irc.commands.Join;

public interface ChannelListener {

	public void userJoined(Join event);
	
}
