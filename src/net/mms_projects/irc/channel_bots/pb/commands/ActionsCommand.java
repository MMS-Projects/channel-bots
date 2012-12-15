package net.mms_projects.irc.channel_bots.pb.commands;

import net.mms_projects.irc.channel_bots.pb.Command;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.CommandSyntax;
import net.mms_projects.irc.channel_bots.pb.PassedData;
import net.mms_projects.irc.channel_bots.pb.command_arguments.SubCommand;

public class ActionsCommand extends Command {

	public ActionsCommand(CommandHandler handler) {
		super("actions", "Used to manage the actions", handler);
		
		this.addSyntax(new CommandSyntax(new SubCommand("action", this.commands
				.toArray(new Command[this.commands.size()]))));
		
		this.addHelp();
	}

	@Override
	public void run(String rawdata, PassedData data) {
		// TODO Auto-generated method stub

	}

}
