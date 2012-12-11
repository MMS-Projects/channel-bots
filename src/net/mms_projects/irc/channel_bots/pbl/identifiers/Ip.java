package net.mms_projects.irc.channel_bots.pbl.identifiers;

import net.mms_projects.irc.channel_bots.pbl.Function;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public class Ip extends Function {

	public Ip() {
		this.name = "ip";
	}
	
	@Override
	public String run(Identifier identifier) {
		return this.handler.getVariable("internal.ip");
	}

}
