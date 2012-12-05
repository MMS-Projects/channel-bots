package net.mms_projects.irc.channel_bots.pbl;

public abstract class LanguageEntity {

	protected Handler handler;
	protected Parser parser;
	
	public LanguageEntity(Handler handler, Parser parser) {
		this.handler = handler;
		this.parser = parser;
	}
	
	abstract public String getOutput();
	
	public String dump() {
		return null;
	}
	
}
