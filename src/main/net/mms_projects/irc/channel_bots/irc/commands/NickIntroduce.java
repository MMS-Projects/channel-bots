package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class NickIntroduce extends Command {

	public String nickname;
	public int hobs;
	public int timestamp;
	public String username;
	public String hostname;
	public String server;
	public int serviceStamp;
	public String realname;
	
	public NickIntroduce() {
		super("NICK (.+) (\\d+) (\\d+) (.+) (.+) (.+) (\\d+) :(.+)", "NICK %s %d %d %s %s %s %d :%s");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = this.match(rawdata);
		if (matcher.find()) {
			this.nickname = matcher.group(1);
			this.hobs = Integer.parseInt(matcher.group(2));
			this.timestamp = Integer.parseInt(matcher.group(3));
			this.username = matcher.group(4);
			this.hostname = matcher.group(5);
			this.server = matcher.group(6);
			this.serviceStamp = Integer.parseInt(matcher.group(7));
			this.realname = matcher.group(8);
		}
	}

	@Override
	public String build() {
		return String.format(this.outputPattern, this.nickname, this.hobs, this.timestamp, this.username, this.hostname, this.server, this.serviceStamp, this.realname);
	}

}
