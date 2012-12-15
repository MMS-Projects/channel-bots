package net.mms_projects.irc.channel_bots.pb.commands;

import net.mms_projects.irc.channel_bots.pb.Command;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.CommandSyntax;
import net.mms_projects.irc.channel_bots.pb.PassedData;
import net.mms_projects.irc.channel_bots.pb.command_arguments.SubCommand;

public class Triggers extends Command {

	public Triggers(CommandHandler handler) {
		super("triggers", "Used to manage triggers", handler);
		
		this.addCommand(new TriggersList(handler, this));
		
		this.addSyntax(new CommandSyntax(new SubCommand("action", this.commands
				.toArray(new Command[this.commands.size()]))));

		this.addHelp();
	}

	@Override
	public void run(String rawdata, PassedData data) {
		boolean handled = this.handle(rawdata, data);
		if (!handled) {
			data.bot.notice(data.event.source, "Please specify a subcommand.");
			this.getHelp().run(rawdata, data);
		}
	}

}
