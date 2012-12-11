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
		String[] tokens = rawdata.split(" ");
		
		if (tokens.length < 4) {
			this.reply(data, "Wrong syntax. The syntax is:");
			this.getHelp().showSyntax(data);
			return;
		}
		data.pblHandler.setVariable(tokens[2].substring(1), tokens[3]);
		this.reply(data, "Set " + tokens[2] + " to: " + tokens[3]);
	}

}
