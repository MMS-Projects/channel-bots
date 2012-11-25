package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class UnknownCommand extends Command {

	public String command;
	
	public UnknownCommand() {
		super("(.+)", "%s");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = match(rawdata);
		if (matcher.find()) {
			this.command = matcher.group(1);
		}
	}

	@Override
	public String build() {
		return String.format(this.outputPattern, this.command);
	}

}
