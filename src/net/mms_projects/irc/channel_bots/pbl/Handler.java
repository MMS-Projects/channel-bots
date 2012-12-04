package net.mms_projects.irc.channel_bots.pbl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mms_projects.irc.channel_bots.pbl.identifiers.Append;
import net.mms_projects.irc.channel_bots.pbl.identifiers.Asc;
import net.mms_projects.irc.channel_bots.pbl.identifiers.Chan;
import net.mms_projects.irc.channel_bots.pbl.identifiers.Chr;
import net.mms_projects.irc.channel_bots.pbl.identifiers.Ctime;
import net.mms_projects.irc.channel_bots.pbl.identifiers.Eval;
import net.mms_projects.irc.channel_bots.pbl.identifiers.Me;
import net.mms_projects.irc.channel_bots.pbl.identifiers.Nick;
import net.mms_projects.irc.channel_bots.pbl.identifiers.Replace;
import net.mms_projects.irc.channel_bots.pbl.identifiers.Time;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;
import net.mms_projects.irc.channel_bots.pbl.language_entities.Variable;

public class Handler {

	public List<Function> functions = new ArrayList<Function>();
	private Map<String, String> variables = new HashMap<String, String>();

	public Handler() {
		this.functions.add(new Time());
		this.functions.add(new Replace());
		this.functions.add(new Ctime());
		this.functions.add(new Chan());
		this.functions.add(new Nick());
		this.functions.add(new Me());
		this.functions.add(new Asc());
		this.functions.add(new Chr());
		this.functions.add(new Eval());
		
		this.functions.add(new Append());
		
		this.setVariable("internal.testvariable", "Test value?");
		
		for (Function function : this.functions) {
			function.setHandler(this);
		}
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
