package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class Away extends Command {

	public String nickname;
	public String status;

	public Away() {
		super(":(.+) AWAY( :(.+))?", ":%s AWAY");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = this.match(rawdata);
		if (matcher.find()) {
			this.nickname = matcher.group(1);
			this.status = matcher.group(3);
		}
	}

	@Override
	public String build() {
		if (status != null) {
			return String.format(this.outputPattern + " :%s", this.nickname, this.status);
		} else {
			return String.format(this.outputPattern, this.nickname);
		}
	}

}
