package net.mms_projects.irc.channel_bots.irc;

import java.util.ArrayList;

import net.mms_projects.irc.channel_bots.irc.commands.Away;
import net.mms_projects.irc.channel_bots.irc.commands.NetInfo;
import net.mms_projects.irc.channel_bots.irc.commands.NickChange;
import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.Ping;
import net.mms_projects.irc.channel_bots.irc.commands.Quit;
import net.mms_projects.irc.channel_bots.irc.commands.SetHost;
import net.mms_projects.irc.channel_bots.listeners.NetworkListener;
import net.mms_projects.irc.channel_bots.listeners.PingPongListener;
import net.mms_projects.irc.channel_bots.listeners.UserUpdateListener;

public class Handler {

	private ArrayList<PingPongListener> pingPongListeners = new ArrayList<PingPongListener>();
	private ArrayList<UserUpdateListener> userUpdateListeners = new ArrayList<UserUpdateListener>();
	private ArrayList<NetworkListener> networkListeners = new ArrayList<NetworkListener>();

	public void addPingPongListener(PingPongListener listener) {
		this.pingPongListeners.add(listener);
	}

	public void addUserUpdateListener(UserUpdateListener listener) {
		this.userUpdateListeners.add(listener);
	}
	
	public void addNetworkListener(NetworkListener listener) {
		this.networkListeners.add(listener);
	}

	public void handle(Command command) {
		System.out.println("Rebuild: \"" + command.build() + "\"");

		if (command instanceof NetInfo) {
			for (NetworkListener listener : this.networkListeners) {
				listener.onNetInfo((NetInfo) command);
			}
		}
		if (command instanceof Ping) {
			for (PingPongListener listener : this.pingPongListeners) {
				listener.onPing((Ping) command);
			}
		}
		if (command instanceof NickIntroduce) {
			for (UserUpdateListener listener : this.userUpdateListeners) {
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
