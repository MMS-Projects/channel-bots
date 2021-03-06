package net.mms_projects.irc.channel_bots.plugins;

import java.util.Date;
import java.util.concurrent.ExecutorService;

import net.mms_projects.irc.channel_bots.Channel;
import net.mms_projects.irc.channel_bots.ChannelBots;
import net.mms_projects.irc.channel_bots.ChannelList;
import net.mms_projects.irc.channel_bots.Messages;
import net.mms_projects.irc.channel_bots.Plugin;
import net.mms_projects.irc.channel_bots.Server;
import net.mms_projects.irc.channel_bots.ServerList;
import net.mms_projects.irc.channel_bots.Socket;
import net.mms_projects.irc.channel_bots.Topic;
import net.mms_projects.irc.channel_bots.User;
import net.mms_projects.irc.channel_bots.UserList;
import net.mms_projects.irc.channel_bots.irc.Handler;
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
import net.mms_projects.irc.channel_bots.listeners.ChannelListener;
import net.mms_projects.irc.channel_bots.listeners.NetworkListener;
import net.mms_projects.irc.channel_bots.listeners.PingPongListener;
import net.mms_projects.irc.channel_bots.listeners.UserUpdateListener;

public class Main extends Plugin implements PingPongListener,
		UserUpdateListener, NetworkListener, ChannelListener {

	public Main(Socket socket, Handler handler, UserList userList,
			ChannelList channelList, ServerList serverList,
			ExecutorService threadPool) {
		super(socket, handler, userList, channelList, serverList, threadPool);

		handler.addPingPongListener(this);
		handler.addUserUpdateListener(this);
		handler.addNetworkListener(this);
		handler.addChannelListener(this);
	}

	@Override
	public void onPing(Ping ping) {
		socket.write("PONG :" + ping.token); //$NON-NLS-1$
	}

	@Override
	public void onNickIntroduced(NickIntroduce event) {
		User user = User.createFromNickIntroduce(event);
		this.userList.add(user);
	}

	@Override
	public void onSetHost(SetHost event) {
		this.userList.updateUserHost(event.nickname, event.hostname);
	}

	@Override
	public void onUserAway(Away event) {
		this.userList.updateUserAwayStatus(event.nickname, event.status);
	}

	@Override
	public void onNickChange(NickChange event) {
		this.userList.updateNickname(event.oldNickname, event.newNickname);
	}

	@Override
	public void onUserQuit(Quit event) {
		for (Channel channel : this.channelList) {
			User user = channel.users.getUserByName(event.nickname);
			if (user != null)
				channel.removeUser(user);
		}
		this.userList.removeUser(event.nickname);
	}

	@Override
	public void onNetInfo(NetInfo event) {
		int timestamp = (int) (new Date().getTime() / 1000);
		int offset = (event.currentTime - timestamp) * 1000;

		String message = String
				.format(Messages.getString("sync.time_offset_message"), offset, event.currentTime, timestamp); //$NON-NLS-1$
		System.out.println(message);
		this.messageOps(message);

		ChannelBots.date.setOffset(offset);

		NetInfo netInfo = new NetInfo();
		netInfo.maxGlobal = 10;
		netInfo.currentTime = (int) (ChannelBots.date.getDate().getTime() / 1000);
		netInfo.protocolVersion = 0;
		netInfo.cloakHash = "*"; //$NON-NLS-1$
		netInfo.networkName = "MMS-Projects IRC"; //$NON-NLS-1$
		socket.write(netInfo.toString());
	}

	public void messageOps(String message) {
		this.socket.write(":channels.mms-projects.net SMO o :" + message); //$NON-NLS-1$
	}

	@Override
	public void onServerIntroduced(ServerIntroduce event) {
		this.serverList.add(Server.createFromServerIntroduced(event));
	}

	@Override
	public void onEOS(EOS command) {
		this.serverList.updateServerSynced(command.source, true);
	}

	@Override
	public void userJoined(Join event) {
		for (String name : event.channels) {
			Channel channel = this.channelList.getChannelByName(name);
			if (channel == null) {
				this.channelList.add(Channel.createFromName(name));
			} else {
				User user = this.userList.getUserByName(event.nickname);
				channel.addUser(user);
			}
		}
	}

	@Override
	public void userLeft(Part event) {
		Channel channel = this.channelList.getChannelByName(event.channel);
		User user = this.userList.getUserByName(event.nickname);
		channel.removeUser(user);
		if (channel.users.size() == 0)
			this.channelList.remove(channel);
	}

	@Override
	public void topicChanged(
			net.mms_projects.irc.channel_bots.irc.commands.Topic event) {
		Topic topic = new Topic(event.nickname, event.timestamp, event.text);
		this.channelList.updateTopic(event.channel, topic);
	}

}
