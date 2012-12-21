package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class Pass extends Command {

	public String password;
	
	public Pass() {
		super("PASS \\:(.+)", "PASS :%s");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = this.match(rawdata);
		if (matcher.find()) {
			this.password = matcher.group(1);
		}
	}

	@Override
	public String build() {
		return String.format(this.outputPattern, password);
	}

}
