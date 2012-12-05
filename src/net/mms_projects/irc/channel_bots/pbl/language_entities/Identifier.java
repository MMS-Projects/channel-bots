package net.mms_projects.irc.channel_bots.pbl.language_entities;

import java.util.ArrayList;
import java.util.List;

import net.mms_projects.irc.channel_bots.pbl.Handler;
import net.mms_projects.irc.channel_bots.pbl.LanguageEntity;
import net.mms_projects.irc.channel_bots.pbl.Parser;

public class Identifier extends LanguageEntity {
	
	public Identifier(Handler handler, Parser parser) {
		super(handler, parser);
	}

	public String name = "";
	public List<String> arguments = new ArrayList<String>();
	public String property;
	
	public String unparsed;
	
	public static int TYPE_NORMAL = 1;
	public static int TYPE_PARAMETERS = 2;
	public static int TYPE_PROPERTY = 3;
	
	public String dump() {
		System.out.println("Name: \"" + name + "\"");
		int i = 0;
		for (String argument : arguments) {
			System.out.println("Argument " + (i + 1) + ": \"" + argument + "\"");
			++i;
		}
		if (this.property != null) {
			System.out.println("Property: \"" + this.property + "\"");
		}
		System.out.println("Unparsed: " + this.unparsed);
		System.out.println("Output: " + this.getOutput());
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
		if (this.property != null) {
			payload += "." + this.property;
		}
		return payload;
	}

	@Override
	public String getOutput() {
		int i = 0;
		for (String output : this.arguments) {
			this.arguments.set(i, this.parser.eval(output));
			++i;
		}
		return this.handler.handleString(this);
	}
	
}
