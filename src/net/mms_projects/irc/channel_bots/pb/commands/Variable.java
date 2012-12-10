package net.mms_projects.irc.channel_bots.pb.commands;

import net.mms_projects.irc.channel_bots.pb.Command;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.CommandSyntax;
import net.mms_projects.irc.channel_bots.pb.PassedData;
import net.mms_projects.irc.channel_bots.pb.command_arguments.SubCommand;
import net.mms_projects.irc.channel_bots.pb.command_arguments.Text;

public class Variable extends Command {

	public Variable(CommandHandler handler) {
		super("variable", "Used to manage channel variables", handler);
		
		this.setLongDescription("Channel variables allow saving " +
				"information. This information can be used by " +
				"triggers and actions. This allows having " +
				"shorter code and easy maintaining of information.");
		
		this.addCommand(new VariableSet(handler, this));
		this.addSyntax(new CommandSyntax(new SubCommand("action", this.commands
				.toArray(new Command[this.commands.size()]))));
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
