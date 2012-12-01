package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.mms_projects.irc.channel_bots.irc.Command;

public class ServerIntroduce extends Command {

	public String server;
	public int hopCount;
	public String description;
	
	// Optional options
	public int protocolVersion;
	public String protocolFlags;
	public int serverNumeric;
	public String serverName;
	
	public ServerIntroduce() {
		super("SERVER (.+) (\\d+) :(.+)", "SERVER %s %d :%s");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = this.match(rawdata);
		if (matcher.find()) {
			this.server = matcher.group(1);
			this.hopCount = Integer.parseInt(matcher.group(2));
			this.description = matcher.group(3);
			
			Pattern extraInfo = Pattern.compile("U(\\d*)-([a-zA-Z]*)-(\\d*) (.+)");
			Matcher extraInfoMatcher = extraInfo.matcher(this.description);
			if (extraInfoMatcher.find()) {
				this.protocolVersion = Integer.parseInt(extraInfoMatcher.group(1));
				this.protocolFlags = extraInfoMatcher.group(2);
				this.serverNumeric = Integer.parseInt(extraInfoMatcher.group(3));
				this.serverName = extraInfoMatcher.group(4);
			}
		}
	}

	@Override
	public String build() {
		String description = this.description;
		if (this.protocolFlags != null) {
			description = String.format("U%d-%s-%d %s", this.protocolVersion, this.protocolFlags, this.serverNumeric, this.serverName);
		}
		return String.format(this.outputPattern, this.server, this.hopCount, description);
	}

}
