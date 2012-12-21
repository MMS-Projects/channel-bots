package net.mms_projects.irc.channel_bots.pb;

import net.mms_projects.irc.channel_bots.irc.Command;
import net.mms_projects.irc.channel_bots.pb.triggers.Join;
import net.mms_projects.irc.channel_bots.pb.triggers.Msg;

public abstract class Trigger {

	public int id;
	public String type;
	public String data;
	
	public Trigger() {
		
	}
	
	public Trigger(int id, String type, String data) {
		this.id = id;
		this.type = type;
		this.data = data;
	}
	
	static public Trigger createTrigger(int id, String type, String data) {
		Trigger trigger = null;
		if (type.equalsIgnoreCase("msg")) {
			trigger = new Msg(id, data);
		} else if (type.equalsIgnoreCase("join")) {
			trigger = new Join(id, data);
		}
		return trigger;
	}
	
	abstract public boolean matches(Command command);
	
}
