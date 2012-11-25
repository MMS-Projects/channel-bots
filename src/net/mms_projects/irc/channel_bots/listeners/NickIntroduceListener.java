package net.mms_projects.irc.channel_bots.listeners;

import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;

public interface NickIntroduceListener {

	public void onNickIntroduced(NickIntroduce event);
	
}
