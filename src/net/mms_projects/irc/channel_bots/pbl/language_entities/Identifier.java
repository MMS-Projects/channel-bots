package net.mms_projects.irc.channel_bots.pbl.language_entities;

import java.util.ArrayList;
import java.util.List;

import net.mms_projects.irc.channel_bots.pbl.LanguageEntity;

public class Identifier extends LanguageEntity {
	
	public List<String> arguments;
	
	public Identifier() {
		arguments = new ArrayList<String>();
	}
	
	public String dump() {
		System.out.println("Name: " + name);
		int i = 0;
		for (String argument : arguments) {
			System.out.println("Argument " + (i + 1) + ": " + argument);
			++i;
		}
		return "";
	}
	
	@Override
	public String toString() {
		String payload = "$";
		payload += name;
		if (!this.arguments.isEmpty()) {
			payload += "(";
			int i = 0;
			for (String argument : arguments) {
				payload += "\"" + argument + "\"";
				i++;
				if (i < arguments.size()) {
					payload += ", ";
				}
			}
			payload += ")";
		}
		return payload;
	}
	
}
