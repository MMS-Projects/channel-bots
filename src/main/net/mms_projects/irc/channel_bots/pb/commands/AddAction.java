package net.mms_projects.irc.channel_bots.pb.commands;

import net.mms_projects.irc.channel_bots.pb.Command;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.PassedData;

public class AddAction extends Command {

	public AddAction(CommandHandler handler, Command parent) {
		super("action", "Adds a action", handler, parent);
		
		this.addHelp();
	}

	@Override
	public void run(String rawdata, PassedData data) {
		data.bot.notice(data.event.source, "Added a action");
	}

}
