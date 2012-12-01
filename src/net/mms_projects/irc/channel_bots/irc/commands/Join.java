package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class Join extends Command {

	public String nickname;
	public String channel;
	
	public Join() {
		super(":(.*) JOIN (.*)", ":%s JOIN %s");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = this.match(rawdata);
		if (matcher.find()) {
			this.nickname = matcher.group(1);
			this.channel = matcher.group(2);
		}
	}

	@Override
	public String build() {
		return String.format(this.outputPattern, this.nickname, this.channel);
	}

}
