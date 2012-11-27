package net.mms_projects.irc.channel_bots.plugins;

import net.mms_projects.irc.channel_bots.Plugin;
import net.mms_projects.irc.channel_bots.Socket;
import net.mms_projects.irc.channel_bots.UserList;
import net.mms_projects.irc.channel_bots.irc.Handler;
import net.mms_projects.irc.channel_bots.irc.commands.Away;
import net.mms_projects.irc.channel_bots.irc.commands.NickChange;
import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.Quit;
import net.mms_projects.irc.channel_bots.irc.commands.SetHost;
import net.mms_projects.irc.channel_bots.listeners.UserUpdateListener;

public class EventDebug extends Plugin implements UserUpdateListener {

	public EventDebug(Socket socket, Handler handler, UserList userList) {
		super(socket, handler, userList);
		
		handler.addUserUpdateListener(this);
	}

	@Override
	public void onSetHost(SetHost event) {
	}

	@Override
	public void onUserAway(Away event) {
	}

	@Override
	public void onNickChange(NickChange event) {
	}

	@Override
	public void onUserQuit(Quit event) {
		System.out.println("Bye " + event.nickname);
	}

	@Override
	public void onNickIntroduced(NickIntroduce event) {
		System.out.println("Hi " + event.nickname + " :D");
	}

}
