package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class Topic extends Command {

	public String channel;
	public String nickname;
	public int timestamp;
	public String text;

	public Topic() {
		super("TOPIC ([^\\s]+) ([^\\s]+) (\\d+) :(.+)", "TOPIC %s %s %d :%s");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = match(rawdata);
		if (matcher.find()) {
			this.channel = matcher.group(1);
			this.nickname = matcher.group(2);
			this.timestamp = Integer.parseInt(matcher.group(3));
			this.text = matcher.group(4);
		}
	}

	@Override
	public String build() {
		return String.format(this.outputPattern, this.channel, this.nickname,
				this.timestamp, this.text);
	}

}
