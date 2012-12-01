package net.mms_projects.irc.channel_bots.listeners;

import net.mms_projects.irc.channel_bots.irc.commands.NetInfo;

public interface NetworkListener {

	public void onNetInfo(NetInfo event);
	
}
