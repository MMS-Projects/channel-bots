package net.mms_projects.irc.channel_bots.listeners;

import net.mms_projects.irc.channel_bots.irc.commands.Away;
import net.mms_projects.irc.channel_bots.irc.commands.NickChange;
import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;
import net.mms_projects.irc.channel_bots.irc.commands.Quit;
import net.mms_projects.irc.channel_bots.irc.commands.SetHost;

public interface UserUpdateListener {

	public void onNickIntroduced(NickIntroduce event);
	
	public void onSetHost(SetHost event);

	public void onUserAway(Away event);
	
	public void onNickChange(NickChange event);

	public void onUserQuit(Quit event);
	
}
