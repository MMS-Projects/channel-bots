package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class Notice extends Command {

	public String source;
	public String target;
	public String text;
	
	public Notice() {
		super(":(.+) NOTICE (.+) :(.*)", ":%s NOTICE %s :%s");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = match(rawdata);
		if (matcher.find()) {
			this.source = matcher.group(1);
			this.target = matcher.group(2);
			this.text = matcher.group(3);
		}
	}

	@Override
	public String build() {
		return String.format(this.outputPattern, this.source, this.target, this.text);
	}

}
