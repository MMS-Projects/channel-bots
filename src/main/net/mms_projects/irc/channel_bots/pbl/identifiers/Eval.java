package net.mms_projects.irc.channel_bots.pbl.identifiers;

import net.mms_projects.irc.channel_bots.pbl.Function;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public class Eval extends Function {

	public Eval() {
		this.name = "eval";
	}

	@Override
	public String run(Identifier identifier) {
		int times = (identifier.arguments.size() > 1) ? Integer
				.parseInt(identifier.arguments.get(1)) : 1;
		String data = identifier.arguments.get(0);
		return this.parser.eval(data, times);
	}

}
