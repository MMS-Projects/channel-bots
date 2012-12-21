package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class Join extends Command {

	public String nickname;
	public String[] channels;
	
	public Join() {
		super(":(.*) JOIN (.*)", ":%s JOIN %s");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = this.match(rawdata);
		if (matcher.find()) {
			this.nickname = matcher.group(1);
			this.channels = matcher.group(2).split(",");
		}
	}

	@Override
	public String build() {
		String channels = "";
		int i = 0;
		for (String name : this.channels) {
			channels += name;
			++i;
			if (i < this.channels.length) {
				channels += ",";
			}
		}
		return String.format(this.outputPattern, this.nickname, channels);
	}

}
