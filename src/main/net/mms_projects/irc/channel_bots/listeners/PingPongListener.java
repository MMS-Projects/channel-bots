package net.mms_projects.irc.channel_bots.listeners;

import net.mms_projects.irc.channel_bots.irc.commands.Ping;

public interface PingPongListener {

	public void onPing(Ping event);
	// public void onPong(Pong event);
	
}
