package net.mms_projects.irc.channel_bots.pb;

import net.mms_projects.irc.channel_bots.pb.actions.Msg;
import net.mms_projects.irc.channel_bots.pb.actions.Notice;

public class Action {

	public int id;
	public int triggerId;
	public int type;
	public String data;

	public Action(int id, int triggerId, int type, String data) {
		this.id = id;
		this.triggerId = triggerId;
		this.type = type;
		this.data = data;
	}

	static public Action create(int id, int triggerId, String type, String data) {
		Action action = null;
		if (type.equalsIgnoreCase("msg")) {
			action = new Msg(id, triggerId, data);
		} else if (type.equalsIgnoreCase("notice")) {
			action = new Notice(id, triggerId, data);
		}
		return action;
	}

}
