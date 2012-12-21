package net.mms_projects.irc.channel_bots.pb.triggers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.mms_projects.irc.channel_bots.irc.Command;
import net.mms_projects.irc.channel_bots.pb.Trigger;

public class Join extends Trigger {
	
	public Join() {
		super();
		
		this.type = "JOIN";
	}
	
	public Join(int id, String data) {
		super(id, "JOIN", data);
	}

	@Override
	public boolean matches(Command command) {
		if (!(command instanceof net.mms_projects.irc.channel_bots.irc.commands.Join)) {
			return false;
		}
		return this.matched((net.mms_projects.irc.channel_bots.irc.commands.Join) command);
	}
	
	public boolean matched(net.mms_projects.irc.channel_bots.irc.commands.Join command) {
		Pattern pattern = Pattern.compile(this.data);
		Matcher matcher = pattern.matcher(command.nickname);
		return matcher.find();
	}

}
