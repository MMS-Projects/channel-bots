package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class Quit extends Command {
	public String nickname;
	public String reason;
	
	public Quit () {
		super(":(.+) QUIT( :(.+))?", ":%s QUIT");
	}
	
	@Override
	public void parse(String rawdata) {
		Matcher matcher = this.match(rawdata);
		if (matcher.find()) {
			this.nickname = matcher.group(1);
			this.reason = matcher.group(3);
		}
	}

	@Override
	public String build() {
		if (reason != null) {
			return String.format(this.outputPattern + " :%s", this.nickname, this.reason);
		} else {
			return String.format(this.outputPattern, this.nickname);
		}
	}
}
