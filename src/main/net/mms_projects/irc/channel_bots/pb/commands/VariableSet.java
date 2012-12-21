package net.mms_projects.irc.channel_bots.pb.commands;

import net.mms_projects.irc.channel_bots.pb.Command;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.CommandSyntax;
import net.mms_projects.irc.channel_bots.pb.PassedData;
import net.mms_projects.irc.channel_bots.pb.command_arguments.Text;

public class VariableSet extends Command {

	public VariableSet(CommandHandler handler, Command parent) {
		super("set", "Used to set a variable", handler, parent);
		
		this.setLongDescription("This sets a channel variable. If a " +
				"channel variable already exists it will be overwriten. " +
				"Be carefull as this happens without a warning.");
		this.addSyntax(new CommandSyntax(new Text("variable"), new Text("value")));
		
		this.addHelp();
	}

	@Override
	public boolean match(String rawdata) {
		return rawdata.startsWith(this.getFullCommand());
	}

	@Override
	public void run(String rawdata, PassedData data) {
		data.bot.notice(data.event.source, "Variable bla");
	}

}
