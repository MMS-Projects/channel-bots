package net.mms_projects.irc.channel_bots.irc;

import java.util.ArrayList;
import java.util.List;

import net.mms_projects.irc.channel_bots.Manager;
import net.mms_projects.irc.channel_bots.Plugin;
import net.mms_projects.irc.channel_bots.irc.commands.Away;
import net.mms_projects.irc.channel_bots.irc.commands.EOS;
import net.mms_projects.irc.channel_bots.irc.commands.Join;
import net.mms_projects.irc.channel_bots.irc.commands.NetInfo;
import net.mms_projects.irc.channel_bots.irc.commands.NickChange;
import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.Notice;
import net.mms_projects.irc.channel_bots.irc.commands.Part;
import net.mms_projects.irc.channel_bots.irc.commands.Ping;
import net.mms_projects.irc.channel_bots.irc.commands.PrivMsg;
import net.mms_projects.irc.channel_bots.irc.commands.Quit;
import net.mms_projects.irc.channel_bots.irc.commands.ServerIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.SetHost;
import net.mms_projects.irc.channel_bots.irc.commands.Topic;
import net.mms_projects.irc.channel_bots.listeners.ChannelListener;
import net.mms_projects.irc.channel_bots.listeners.MessageListener;
import net.mms_projects.irc.channel_bots.listeners.NetworkListener;
import net.mms_projects.irc.channel_bots.listeners.PingPongListener;
import net.mms_projects.irc.channel_bots.listeners.UserUpdateListener;

public class Handler {

	private List<Plugin> plugins = new ArrayList<Plugin>();
	private List<Manager> managers = new ArrayList<Manager>();
	
	private List<PingPongListener> pingPongListeners = new ArrayList<PingPongListener>();
	private List<UserUpdateListener> userUpdateListeners = new ArrayList<UserUpdateListener>();
	private List<NetworkListener> networkListeners = new ArrayList<NetworkListener>();
	private List<ChannelListener> channelListeners = new ArrayList<ChannelListener>();
	private List<MessageListener> messageListeners = new ArrayList<MessageListener>();

	public void addPlugin(Plugin plugin) {
		this.plugins.add(plugin);
	}

	public void addManager(Manager manager) {
		this.managers.add(manager);
	}
	
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

	public void addMessageListener(MessageListener listener) {
		this.messageListeners.add(listener);
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
		if (command instanceof PrivMsg) {
			for (MessageListener listener : this.messageListeners) {
				listener.onPrivMsg((PrivMsg) command);
			}
		}
		if (command instanceof Notice) {
			for (MessageListener listener : this.messageListeners) {
				listener.onNotice((Notice) command);
			}
		}
	}

	public void tick() {
		for (Manager manager : managers) {
			manager.tick();
		}
		for (Plugin plugin : plugins) {
			plugin.tick();
		}
	}

}
