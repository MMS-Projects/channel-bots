package net.mms_projects.irc.channel_bots.pb.actions;

import net.mms_projects.irc.channel_bots.pb.Action;

public class Msg extends Action {

	public Msg(int id, int triggerId, String data) {
		super(id, triggerId, 1, data);
	}

}
