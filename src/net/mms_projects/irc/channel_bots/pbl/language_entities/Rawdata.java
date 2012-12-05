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
	public void dump(String prefix) {
		int i = 0;
		for (LanguageEntity entity : this.data) {
			System.out.println(prefix + "Data " + (i + 1) + ": " + entity.getClass().getSimpleName());
			entity.dump(prefix + "    ");
			++i;
		}
	}

	@Override
	public String getOutput() {
		String output = "";
		for (LanguageEntity entity : this.data) {
			if (entity != null) {
				output += entity.getOutput();
			} else {
				output += null;
			}
		}
		output = output.replace(" " + Append.character + " ", "");
		return output;
	}
	
}