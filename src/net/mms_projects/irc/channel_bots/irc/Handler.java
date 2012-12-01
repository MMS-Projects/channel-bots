package net.mms_projects.irc.channel_bots.irc;

import java.util.ArrayList;

import net.mms_projects.irc.channel_bots.irc.commands.Away;
import net.mms_projects.irc.channel_bots.irc.commands.EOS;
import net.mms_projects.irc.channel_bots.irc.commands.Join;
import net.mms_projects.irc.channel_bots.irc.commands.NetInfo;
import net.mms_projects.irc.channel_bots.irc.commands.NickChange;
import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.Part;
import net.mms_projects.irc.channel_bots.irc.commands.Ping;
import net.mms_projects.irc.channel_bots.irc.commands.Quit;
import net.mms_projects.irc.channel_bots.irc.commands.ServerIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.SetHost;
import net.mms_projects.irc.channel_bots.irc.commands.Topic;
import net.mms_projects.irc.channel_bots.listeners.ChannelListener;
import net.mms_projects.irc.channel_bots.listeners.NetworkListener;
import net.mms_projects.irc.channel_bots.listeners.PingPongListener;
import net.mms_projects.irc.channel_bots.listeners.UserUpdateListener;

public class Handler {

	private ArrayList<PingPongListener> pingPongListeners = new ArrayList<PingPongListener>();
	private ArrayList<UserUpdateListener> userUpdateListeners = new ArrayList<UserUpdateListener>();
	private ArrayList<NetworkListener> networkListeners = new ArrayList<NetworkListener>();
	private ArrayList<ChannelListener> channelListeners = new ArrayList<ChannelListener>();

	public void addPingPongListener(PingPongListener listener) {
		this.pingPongListeners.add(listener);
	}

	public void addUserUpdateListener(UserUpdateListener listener) {
		this.userUpdateListeners.add(listener);
	}

	public void addNetworkListener(NetworkListener listener) {
		this.networkListeners.add(listener);
	}

	public void addChannelListener(ChannelListener listener) {
		this.channelListeners.add(listener);

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
		if (command instanceof ServerIntroduce) {
			for (NetworkListener listener : this.networkListeners) {
				listener.onServerIntroduced((ServerIntroduce) command);
			}
		}
		if (command instanceof Join) {
			for (ChannelListener listener : this.channelListeners) {
				listener.userJoined((Join) command);
			}
		}
		if (command instanceof Part) {
			for (ChannelListener listener : this.channelListeners) {
				listener.userLeft((Part) command);
			}
		}
		if (command instanceof EOS) {
			for (NetworkListener listener : this.networkListeners) {
				listener.onEOS((EOS) command);
			}
		}
		if (command instanceof Topic) {
			for (ChannelListener listener : this.channelListeners) {
				listener.topicChanged((Topic) command);
			}
		}
	}

}
