package net.mms_projects.irc.channel_bots.pbl.language_entities;

import net.mms_projects.irc.channel_bots.pbl.Handler;
import net.mms_projects.irc.channel_bots.pbl.LanguageEntity;
import net.mms_projects.irc.channel_bots.pbl.Parser;

public class Text extends LanguageEntity {

	public Text(Handler handler, Parser parser) {
		super(handler, parser);
	}

	public String text = "";
	
	@Override
	public String toString() {
		return text;
	}

	@Override
	public String getOutput() {
		return toString();
	}
	
}
