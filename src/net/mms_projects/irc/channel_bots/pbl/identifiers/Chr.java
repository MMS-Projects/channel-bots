package net.mms_projects.irc.channel_bots.pbl.identifiers;

import net.mms_projects.irc.channel_bots.pbl.Function;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public class Chr extends Function {

	public Chr() {
		this.name = "chr";
	}
	
	@Override
	public String run(Identifier identifier) {
		int ascii;
		try {
			ascii = Integer.parseInt(identifier.arguments.get(0));
		} catch (NumberFormatException e) {
			return "";
		}
		char character = (char) ascii;
		return Character.toString(character);
	}

}
