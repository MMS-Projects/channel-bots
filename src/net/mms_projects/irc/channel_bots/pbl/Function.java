package net.mms_projects.irc.channel_bots.pbl;

import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public abstract class Function {

	public String name;
	
	public abstract String run(Identifier identifier);
	
}
