package net.mms_projects.irc.channel_bots.pbl.identifiers;

import net.mms_projects.irc.channel_bots.pbl.Function;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public class Replace extends Function {

	public Replace() {
		this.name = "replace";
	}
	
	@Override
	public String run(Identifier identifier) {
		String string = identifier.arguments.get(0);
		int i = 1;
		while (i < identifier.arguments.size()) {
			string = string.replaceAll(identifier.arguments.get(i), identifier.arguments.get(i + 1));
			i += 2;
		}
		return string;
	}

}
