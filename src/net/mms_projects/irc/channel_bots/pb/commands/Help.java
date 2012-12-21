package net.mms_projects.irc.channel_bots.pb.commands;

import net.mms_projects.irc.channel_bots.Messages;
import net.mms_projects.irc.channel_bots.pb.Command;
import net.mms_projects.irc.channel_bots.pb.CommandArgument;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.CommandSyntax;
import net.mms_projects.irc.channel_bots.pb.PassedData;
import net.mms_projects.irc.channel_bots.pb.command_arguments.Text;

public class Help extends Command {

	private Command subject;

	public Help(CommandHandler handler, Command subject) {
		super(
				"help", Messages.getString("pb.command.help.short_description"), handler); //$NON-NLS-1$ //$NON-NLS-2$

		this.subject = subject;
		this.showInList = false;
	}

	public Help(CommandHandler handler) {
		super(
				"help", Messages.getString("pb.command.help.short_description"), handler); //$NON-NLS-1$ //$NON-NLS-2$

		this.addSyntax(new CommandSyntax(new Text(Messages
				.getString("Help.pb.command.help.argument.subject")))); //$NON-NLS-1$
		this.setLongDescription(Messages
				.getString("pb.command.help.long_description")); //$NON-NLS-1$
		
		this.subject = this;
		this.addHelp();
	}

	@Override
	public void run(String rawdata, PassedData data) {
		boolean helped = false;
		CommandHandler subject = null;
		if (this.subject == this) {
			subject = this.handler;
		} else {
			subject = this.subject;
		}
		if (this.subject.longDescription != null) {
			this.reply(data, this.subject.longDescription);
			this.reply(data, "- "); //$NON-NLS-1$
			helped = true;
		}
		if (subject.hasCommands()) {
			this.reply(
					data,
					String.format(
							Messages.getString("pb.command.help.text.for_more_information"), data.bot.nickname, //$NON-NLS-1$
							this.getFullCommand()), 200);
			subject.showCommands(data, 2);
			helped = true;
		}
		boolean syntaxHelped = this.showSyntax(data);
		if (syntaxHelped) {
			helped = syntaxHelped;
		}
		if (!helped) {
			this.reply(data,
					"There is no information available about this command.");
		}
	}

	public boolean showSyntax(PassedData data) {
		boolean helped = false;
		for (CommandSyntax syntax : this.subject.syntaxes) {
			String syntaxText = ""; //$NON-NLS-1$
			for (CommandArgument argument : syntax.arguments) {
				syntaxText += ""; //$NON-NLS-1$
				if ((argument.defaults != null)
						&& (argument.defaults.length != 0)) {
					syntaxText += "["; //$NON-NLS-1$
					for (String example : argument.defaults) {
						syntaxText += example + "/"; //$NON-NLS-1$
					}
					syntaxText = syntaxText.substring(0,
							syntaxText.length() - 1) + "]"; //$NON-NLS-1$
				} else {
					syntaxText += argument.name;
				}
				syntaxText += " "; //$NON-NLS-1$
			}
			this.reply(
					data,
					"Syntax: " + this.subject.getFullCommand() + " " + syntaxText + ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			helped = true;
		}
		if (!helped) {
			this.reply(data, Messages.getString("pb.no_help_available")); //$NON-NLS-1$
		}
		return helped;
	}

	@Override
	public String getFullCommand() {
		if (this.subject == this) {
			return super.getFullCommand();
		}
		return super.getFullCommand() + " " + this.subject.getFullCommand(); //$NON-NLS-1$
	}

	@Override
	public boolean match(String rawdata) {
		return rawdata.equalsIgnoreCase(this.getFullCommand());
	}

}
