package net.mms_projects.irc.channel_bots.pbl;

import java.util.ArrayList;
import java.util.List;

import net.mms_projects.irc.channel_bots.pbl.identifiers.Time;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public class Handler {

	private List<Function> functions = new ArrayList<Function>();
	
	public Handler() {
		this.functions.add(new Time());
	}
	
	public List<Character> handle(Identifier identifier) {
		String returnValue = "";
		List<Character> returnList = new ArrayList<Character>();
		for (Function function : this.functions) {
			if (function.name.equals(identifier.name)) {
				returnValue = function.run(identifier);
				break;
			}
		}
		for (char c : returnValue.toCharArray()) {
			returnList.add(c);
		}
		return returnList;
	}
	
}
