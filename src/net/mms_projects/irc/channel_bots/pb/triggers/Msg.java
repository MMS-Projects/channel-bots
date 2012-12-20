package net.mms_projects.irc.channel_bots.pb.triggers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.mms_projects.irc.channel_bots.irc.Command;
import net.mms_projects.irc.channel_bots.irc.commands.PrivMsg;
import net.mms_projects.irc.channel_bots.pb.Trigger;

public class Msg extends Trigger {

	public Msg() {
		super();
		
		this.type = "MSG";
	}
	
	public Msg(int id, String data) {
		super(id, "MSG", data);
	}

	@Override
	public boolean matches(Command command) {
		if (!(command instanceof PrivMsg)) {
			return false;
		}
		return this.matches((PrivMsg) command);
	}
	
	public boolean matches(PrivMsg command) {
		Pattern pattern = Pattern.compile(this.data);
		Matcher matcher = pattern.matcher(command.text);
		return matcher.find();
	}

}
