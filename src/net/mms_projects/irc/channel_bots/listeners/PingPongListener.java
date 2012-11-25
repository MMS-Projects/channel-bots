package net.mms_projects.irc.channel_bots.listeners;

import net.mms_projects.irc.channel_bots.irc.commands.Ping;

public interface PingPongListener {

	public void onPing(Ping ping);
	// public void onPong(Pong ping);
	
}
