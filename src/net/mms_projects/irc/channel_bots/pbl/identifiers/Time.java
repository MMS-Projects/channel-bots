package net.mms_projects.irc.channel_bots.pbl.identifiers;

import net.mms_projects.irc.channel_bots.ChannelBots;
import net.mms_projects.irc.channel_bots.pbl.Function;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public class Time extends Function {

	public Time() {
		this.name = "time";
	}
	
	@Override
	public String run(Identifier identifier) {
		return ChannelBots.date.getDate().toString();
	}

}
