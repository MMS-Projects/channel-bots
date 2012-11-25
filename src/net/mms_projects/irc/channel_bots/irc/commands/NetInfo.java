package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class NetInfo extends Command {

	public int maxGlobal;
	public int currentTime;
	public int protocolVersion;
	public String cloakHash;
	public String networkName;
	
	public NetInfo() {
		super("NETINFO (\\d+) (\\d+) (\\d+) (.+) 0 0 0 :(.+)", "NETINFO %d %d %d %s 0 0 0 :%s");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = this.match(rawdata);
		if (matcher.find()) {
			this.maxGlobal = Integer.parseInt(matcher.group(1));
			this.currentTime = Integer.parseInt(matcher.group(2));
			this.protocolVersion = Integer.parseInt(matcher.group(3));
			this.cloakHash = matcher.group(4);
			this.networkName = matcher.group(5);
		}
	}

	@Override
	public String build() {
		return String.format(this.outputPattern, this.maxGlobal, this.currentTime, this.protocolVersion, this.cloakHash, this.networkName);
	}

}
