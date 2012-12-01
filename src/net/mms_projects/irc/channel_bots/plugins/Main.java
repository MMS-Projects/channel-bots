package net.mms_projects.irc.channel_bots.plugins;

import java.util.Date;

import net.mms_projects.irc.channel_bots.ChannelBots;
import net.mms_projects.irc.channel_bots.Plugin;
import net.mms_projects.irc.channel_bots.Socket;
import net.mms_projects.irc.channel_bots.User;
import net.mms_projects.irc.channel_bots.UserList;
import net.mms_projects.irc.channel_bots.irc.Handler;
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

public class Main extends Plugin implements PingPongListener,
		UserUpdateListener, NetworkListener {

	public Main(Socket socket, Handler handler, UserList userList) {
		super(socket, handler, userList);

		handler.addPingPongListener(this);
		handler.addUserUpdateListener(this);
		handler.addNetworkListener(this);
	}

	@Override
	public void onPing(Ping ping) {
		socket.write("PONG :" + ping.token);
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
		this.userList.removeUser(event.nickname);
	}

	@Override
	public void onNetInfo(NetInfo event) {
		int timestamp = (int) (new Date().getTime() / 1000);
		int offset = (event.currentTime - timestamp) * 1000;

		String message = "Set timestamp offset to " + offset + ". Server time: " + event.currentTime + ". Service time: " + timestamp;
		System.out.println(message);
		this.messageOps(message);
		
		ChannelBots.date.setOffset(offset);
		
		NetInfo netInfo = new NetInfo();
		netInfo.maxGlobal = 10;
		netInfo.currentTime = (int) (ChannelBots.date.getDate().getTime() / 1000);
		netInfo.protocolVersion = 0;
		netInfo.cloakHash = "*";
		netInfo.networkName = "MMS-Projects IRC";
		socket.write(netInfo.toString());
	}

	public void messageOps(String message) {
		this.socket.write(":channels.mms-projects.net SMO o :" + message);
	}
	
}
