package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class NickChange extends Command {

	public String oldNickname;
	public String newNickname;
	public int timestamp;
	
	public NickChange() {
		super(":(.+) NICK (.+) (\\d+)", ":%s NICK %s %d");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = match(rawdata);
		if (matcher.find()) {
			this.oldNickname = matcher.group(1);
			this.newNickname = matcher.group(2);
			this.timestamp = Integer.parseInt(matcher.group(3));
		}
	}

	@Override
	public String build() {
		return String.format(this.outputPattern, this.oldNickname, this.newNickname, this.timestamp);
	}

}
