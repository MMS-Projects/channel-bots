package net.mms_projects.irc.channel_bots.pbl.identifiers;

import net.mms_projects.irc.channel_bots.pbl.Function;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public class Append extends Function {

	public Append() {
		this.name = "+";
	}
	
	@Override
	public String run(Identifier identifier) {
		String string = "";
		for (String parameter : identifier.arguments) {
			string += parameter;
		}
		return string;
	}

}
