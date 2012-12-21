package net.mms_projects.irc.channel_bots.pb.actions;

import net.mms_projects.irc.channel_bots.pb.Action;

public class Notice extends Action {

	public Notice(int id, int triggerId, String data) {
		super(id, triggerId, 2, data);
	}

}
