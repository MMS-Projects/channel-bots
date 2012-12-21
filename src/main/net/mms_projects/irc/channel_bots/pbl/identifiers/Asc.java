package net.mms_projects.irc.channel_bots.pbl.identifiers;

import net.mms_projects.irc.channel_bots.pbl.Function;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public class Asc extends Function {

	public Asc() {
		this.name = "asc";
	}
	
	@Override
	public String run(Identifier identifier) {
		char[] chars = identifier.arguments.get(0).toCharArray();
		int ascii = (int) chars[0];
		return Integer.toString(ascii);
	}

}
