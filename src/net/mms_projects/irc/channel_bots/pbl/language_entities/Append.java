package net.mms_projects.irc.channel_bots.pbl.language_entities;

import net.mms_projects.irc.channel_bots.pbl.Handler;
import net.mms_projects.irc.channel_bots.pbl.LanguageEntity;
import net.mms_projects.irc.channel_bots.pbl.Parser;

public class Append extends LanguageEntity {

	static String character = "\u5001";
	
	public Append(Handler handler, Parser parser) {
		super(handler, parser);
	}

	@Override
	public String getOutput() {
		return Append.character;
	}

}
