package net.mms_projects.irc.channel_bots.listeners;

import net.mms_projects.irc.channel_bots.irc.commands.Notice;
import net.mms_projects.irc.channel_bots.irc.commands.PrivMsg;

public interface MessageListener {

	public void onPrivMsg(PrivMsg event);

	public void onNotice(Notice event);

}
