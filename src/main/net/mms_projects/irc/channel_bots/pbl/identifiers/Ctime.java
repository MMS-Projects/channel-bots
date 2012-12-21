package net.mms_projects.irc.channel_bots.pbl.identifiers;

import net.mms_projects.irc.channel_bots.ChannelBots;
import net.mms_projects.irc.channel_bots.pbl.Function;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public class Ctime extends Function {

	public Ctime() {
		this.name = "ctime";
	}
	
	@Override
	public String run(Identifier identifier) {
		return Long.toString(ChannelBots.date.getDate().getTime() / 1000);
	}

}
