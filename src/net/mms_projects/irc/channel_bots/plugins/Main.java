package net.mms_projects.irc.channel_bots.plugins;

import net.mms_projects.irc.channel_bots.Plugin;
import net.mms_projects.irc.channel_bots.Socket;
import net.mms_projects.irc.channel_bots.User;
import net.mms_projects.irc.channel_bots.UserList;
import net.mms_projects.irc.channel_bots.irc.Handler;
import net.mms_projects.irc.channel_bots.irc.commands.Away;
import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.Ping;
import net.mms_projects.irc.channel_bots.irc.commands.SetHost;
import net.mms_projects.irc.channel_bots.listeners.NickIntroduceListener;
import net.mms_projects.irc.channel_bots.listeners.PingPongListener;
import net.mms_projects.irc.channel_bots.listeners.UserUpdateListener;

public class Main extends Plugin implements PingPongListener,
		NickIntroduceListener, UserUpdateListener {

	public Main(Socket socket, Handler handler, UserList userList) {
		super(socket, handler, userList);

		handler.addPingPongListener(this);
		handler.addNickIntroduceListener(this);
		handler.addUserUpdateListener(this);
	}

	@Override
	public void onPing(Ping ping) {
		socket.write("PONG :" + ping.token);
	}

	@Override
	public void onNickIntroduced(NickIntroduce event) {
		User user = User.createFromNickIntroduce(event);
		this.userList.add(user);
		System.out.println("Hi " + event.nickname + " :D");
	}

	@Override
	public void onSetHost(SetHost event) {
		this.userList.updateUserHost(event.nickname, event.hostname);
	}

	@Override
	public void onUserAway(Away event) {
		this.userList.updateUserAwayStatus(event.nickname, event.status);
	}

}
