package net.mms_projects.irc.channel_bots.pbl;

import net.mms_projects.irc.channel_bots.pbl.language_entities.Identifier;

public abstract class Function {

	public String name;
	protected Handler handler;
	protected Parser parser;
	
	public abstract String run(Identifier identifier);
	
	final public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	final public void setParser(Parser parser) {
		this.parser = parser;
	}
	
}
