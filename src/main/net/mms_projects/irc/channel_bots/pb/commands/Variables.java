package net.mms_projects.irc.channel_bots.pb.commands;

import net.mms_projects.irc.channel_bots.pb.Command;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.CommandSyntax;
import net.mms_projects.irc.channel_bots.pb.PassedData;
import net.mms_projects.irc.channel_bots.pb.command_arguments.SubCommand;

public class Variables extends Command {

	public Variables(CommandHandler handler) {
		super("variables", "Used to manage channel variables", handler);
		
		this.setLongDescription("Channel variables allow saving " +
				"information. This information can be used by " +
				"triggers and actions. This allows having " +
				"shorter code and easy maintaining of information.");
		
		this.addCommand(new VariablesSet(handler, this));
		this.addSyntax(new CommandSyntax(new SubCommand("action", this.commands
				.toArray(new Command[this.commands.size()]))));
		this.addHelp();
	}

	@Override
	public void run(String rawdata, PassedData data) {
		boolean handled = this.handle(rawdata, data);
		if (!handled) {
			data.bot.notice(data.event.source, "Variable bla");
		}
	}

}
