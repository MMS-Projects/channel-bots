package net.mms_projects.irc.channel_bots.irc;

import java.util.ArrayList;

import net.mms_projects.irc.channel_bots.irc.commands.Away;
import net.mms_projects.irc.channel_bots.irc.commands.NickChange;
import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.Ping;
import net.mms_projects.irc.channel_bots.irc.commands.Quit;
import net.mms_projects.irc.channel_bots.irc.commands.SetHost;
import net.mms_projects.irc.channel_bots.listeners.PingPongListener;
import net.mms_projects.irc.channel_bots.listeners.NickIntroduceListener;
import net.mms_projects.irc.channel_bots.listeners.UserUpdateListener;

public class Handler {

	private ArrayList<PingPongListener> pingPongListeners = new ArrayList<PingPongListener>();
	private ArrayList<NickIntroduceListener> nickIntroduceListeners = new ArrayList<NickIntroduceListener>();
	private ArrayList<UserUpdateListener> userUpdateListeners = new ArrayList<UserUpdateListener>();

	public void addPingPongListener(PingPongListener listener) {
		this.pingPongListeners.add(listener);
	}

	public void addNickIntroduceListener(NickIntroduceListener listener) {
		this.nickIntroduceListeners.add(listener);
	}

	public void addUserUpdateListener(UserUpdateListener listener) {
		this.userUpdateListeners.add(listener);
	}

	public void handle(Command command) {
		System.out.println("Rebuild: \"" + command.build() + "\"");

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
		if (command instanceof SetHost) {
			for (UserUpdateListener listener : this.userUpdateListeners) {
				listener.onSetHost((SetHost) command);
			}
		}
		if (command instanceof Away) {
			for (UserUpdateListener listener : this.userUpdateListeners) {
				listener.onUserAway((Away) command);
			}
		}
		if (command instanceof NickChange) {
			for (UserUpdateListener listener : this.userUpdateListeners) {
				listener.onNickChange((NickChange) command);
			}
		}
		if (command instanceof Quit) {
			for (UserUpdateListener listener : this.userUpdateListeners) {
				listener.onUserQuit((Quit) command);
			}
		}
	}

}
