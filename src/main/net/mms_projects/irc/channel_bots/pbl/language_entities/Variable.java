package net.mms_projects.irc.channel_bots.pbl.language_entities;

import net.mms_projects.irc.channel_bots.pbl.Handler;
import net.mms_projects.irc.channel_bots.pbl.LanguageEntity;
import net.mms_projects.irc.channel_bots.pbl.Parser;

public class Variable extends LanguageEntity {

	public Variable(Handler handler, Parser parser) {
		super(handler, parser);
	}

	public String name;
	
	@Override
	public void dump(String prefix) {
	}

	@Override
	public String getOutput() {
		return this.handler.getVariable(name);
	}
	
}
