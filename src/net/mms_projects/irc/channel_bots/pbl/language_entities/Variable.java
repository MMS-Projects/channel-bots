package net.mms_projects.irc.channel_bots.pbl.language_entities;

import net.mms_projects.irc.channel_bots.pbl.LanguageEntity;

public class Variable extends LanguageEntity {

	public String name;
	
	@Override
	public String dump() {
		System.out.println("Variable name: " + this.name);
		return null;
	}
	
}
