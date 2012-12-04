package net.mms_projects.irc.channel_bots.pbl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mms_projects.irc.channel_bots.pbl.identifiers.Ctime;
import net.mms_projects.irc.channel_bots.pbl.identifiers.Replace;
import net.mms_projects.irc.channel_bots.pbl.identifiers.Time;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Variable;

public class Handler {

	private List<Function> functions = new ArrayList<Function>();
	private Map<String, String> variables = new HashMap<String, String>();

	public Handler() {
		this.functions.add(new Time());
		this.functions.add(new Replace());
		this.functions.add(new Ctime());
	}

	public void setVariable(String variable, String value) {
		this.variables.put(variable, value);
	}

	public String getVariable(String variable) {
		return (this.variables.containsKey(variable)) ? this.variables.get(variable) : "";
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

	public List<Character> handle(Variable variable) {
		System.out.println("Checking value of " + variable.name
				+ ". Well it is: " + this.getVariable(variable.name));
		String returnValue = this.getVariable(variable.name);
		List<Character> returnList = new ArrayList<Character>();
		for (char c : returnValue.toCharArray()) {
			returnList.add(c);
		}
		return returnList;
	}
}
