package net.mms_projects.irc.channel_bots.pb.commands;

import net.mms_projects.irc.channel_bots.pb.Command;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.CommandSyntax;
import net.mms_projects.irc.channel_bots.pb.PassedData;
import net.mms_projects.irc.channel_bots.pb.command_arguments.SubCommand;
import net.mms_projects.irc.channel_bots.pb.command_arguments.Text;

public class Add extends Command {

	public Add(CommandHandler handler) {
		super("add", "Adds a entity to the channel.", handler);

		this.addCommand(new AddTrigger(handler, this));
		this.addCommand(new AddAction(handler, this));

		this.addSyntax(new CommandSyntax(new SubCommand("type", this.commands
				.toArray(new Command[this.commands.size()]))));

		this.addHelp();
	}

	@Override
	public boolean match(String rawdata) {
		return rawdata.startsWith(this.getFullCommand());
	}

	@Override
	public void run(String rawdata, PassedData data) {
		boolean handled = this.handle(rawdata, data);
		if (!handled) {
			data.bot.notice(data.event.source, "Added some blabla");
			this.getHelp().run(rawdata, data);
		}
	}

}
