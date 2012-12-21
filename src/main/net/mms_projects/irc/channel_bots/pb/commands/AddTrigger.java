package net.mms_projects.irc.channel_bots.pb.commands;

import net.mms_projects.irc.channel_bots.pb.Command;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.CommandSyntax;
import net.mms_projects.irc.channel_bots.pb.PassedData;
import net.mms_projects.irc.channel_bots.pb.command_arguments.Text;

public class AddTrigger extends Command {

	public AddTrigger(CommandHandler handler, Command parent) {
		super("trigger", "Adds a trigger", handler, parent);
		
		this.addSyntax(new CommandSyntax(new Text("trigger"), new Text("type"), new Text("data")));
		
		this.addHelp();
	}

	@Override
	public boolean match(String rawdata) {
		return rawdata.startsWith(this.getFullCommand());
	}

	@Override
	public void run(String rawdata, PassedData data) {
		data.bot.notice(data.event.source, "Added a trigger");
	}

}
