package net.mms_projects.irc.channel_bots.irc;

import java.util.ArrayList;

import net.mms_projects.irc.channel_bots.irc.commands.Ping;
import net.mms_projects.irc.channel_bots.listeners.PingPongListener;

public class Handler {

	private ArrayList<PingPongListener> pingPongListeners = new ArrayList<PingPongListener>();
	
	public void addPingPongListener(PingPongListener listener) {
		this.pingPongListeners.add(listener);
	}

	public void handle(Command command) {
		if (command instanceof Ping) {
			for (PingPongListener listener : this.pingPongListeners) {
				listener.onPing((Ping) command);
			}
		}
	}
	
}
