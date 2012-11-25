package net.mms_projects.irc.channel_bots.irc;

import java.util.ArrayList;

import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.Ping;
import net.mms_projects.irc.channel_bots.listeners.PingPongListener;
import net.mms_projects.irc.channel_bots.listeners.NickIntroduceListener;

public class Handler {

	private ArrayList<PingPongListener> pingPongListeners = new ArrayList<PingPongListener>();
	private ArrayList<NickIntroduceListener> nickIntroduceListeners = new ArrayList<NickIntroduceListener>();
	
	public void addPingPongListener(PingPongListener listener) {
		this.pingPongListeners.add(listener);
	}
	
	public void addNickIntroduceListener(NickIntroduceListener listener) {
		this.nickIntroduceListeners.add(listener);
	}

	public void handle(Command command) {
		System.out.println("Rebuild: " + command.build());
		
		if (command instanceof Ping) {
			for (PingPongListener listener : this.pingPongListeners) {
				listener.onPing((Ping) command);
			}
		}
		if (command instanceof NickIntroduce) {
			for (NickIntroduceListener listener : this.nickIntroduceListeners) {
				listener.onNickIntroduced((NickIntroduce) command);
			}
		}
	}
	
}
