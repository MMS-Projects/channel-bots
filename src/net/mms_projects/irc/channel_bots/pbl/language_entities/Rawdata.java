package net.mms_projects.irc.channel_bots.pbl.language_entities;

import java.util.ArrayList;
import java.util.List;

import net.mms_projects.irc.channel_bots.pbl.Handler;
import net.mms_projects.irc.channel_bots.pbl.LanguageEntity;
import net.mms_projects.irc.channel_bots.pbl.Parser;

public class Rawdata extends LanguageEntity {

	public Rawdata(Handler handler, Parser parser) {
		super(handler, parser);
	}

	private List<LanguageEntity> data = new ArrayList<LanguageEntity>();
	
	public void addEntity(LanguageEntity entity) {
		this.data.add(entity);
	}
	
	@Override
	public String dump() {
		int i = 0;
		for (LanguageEntity entity : this.data) {
			//System.out.println("Data " + (i + 1) + ": \"" + entity + "\"");
			entity.dump();
			++i;
		}
		return null;
	}

	@Override
	public String getOutput() {
		String output = "";
		int i = 0;
		for (LanguageEntity entity : this.data) {
			if (entity != null) {
				output += entity.getOutput();
			} else {
				output += null;
			}
			++i;
		}
		output = output.replace(" " + Append.character + " ", "");
		return output;
	}
	
}
;