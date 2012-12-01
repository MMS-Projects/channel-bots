package net.mms_projects.irc.channel_bots.listeners;

import net.mms_projects.irc.channel_bots.irc.commands.NetInfo;
import net.mms_projects.irc.channel_bots.irc.commands.ServerIntroduce;

public interface NetworkListener {

	public void onNetInfo(NetInfo event);
	public void onServerIntroduced(ServerIntroduce event);
	
}
