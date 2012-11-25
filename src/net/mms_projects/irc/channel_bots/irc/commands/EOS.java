package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class EOS extends Command {

	public String source;
	
	public EOS() {
		super(":(.+) EOS", ":%s EOS");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = this.match(rawdata);
		if (matcher.find()) {
			this.source = matcher.group(1);
		}
	}

	@Override
	public String build() {
		return String.format(this.outputPattern, this.source);
	}

}
