package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class Ping extends Command {

	public String token;
	
	public Ping() {
		super("PING :(.+)", "PING :%s");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = this.match(rawdata);
		if (matcher.find()) {
			this.token = matcher.group(1);
		}
	}

	@Override
	public String build() {
		return String.format(this.outputPattern, this.token);
	}

}
