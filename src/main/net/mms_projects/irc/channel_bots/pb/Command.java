package net.mms_projects.irc.channel_bots.pb;

import java.util.ArrayList;
import java.util.List;

import net.mms_projects.irc.channel_bots.pb.commands.Help;

public abstract class Command extends CommandHandler {

	public String command;
	public String shortDescription;
	public String longDescription;
	public Command parent;
	public boolean showInList = true;
	public List<CommandSyntax> syntaxes = new ArrayList<CommandSyntax>();

	protected CommandHandler handler;
	protected Help help;

	public Command(String command, String description, CommandHandler handler,
			Command parent) {
		this.command = command;
		this.shortDescription = description;
		this.handler = handler;
		this.parent = parent;
	}

	public Command(String command, String description, CommandHandler handler) {
		this.command = command;
		this.shortDescription = description;
		this.handler = handler;
	}

	public void addHelp() {
		this.help = new Help(this.handler, this);
		this.handler.addCommand(this.help);
	}

	public Help getHelp() {
		return this.help;
	}
	
	public void addSyntax(CommandSyntax syntax) {
		this.syntaxes.add(syntax);
	}
	
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getFullCommand() {
		String command = "";
		if (this.parent != null) {
			command += this.parent.getFullCommand() + " ";
		}
		command += this.command;
		return command;
	}

	public void reply(PassedData data, String text) {
		this.reply(data, text, 50);
	}

	public void reply(PassedData data, String text, int wrap) {
		String[] words = text.split(" ");
		String line = "";
		int word = 0;
		while (word < words.length) {
			while (line.length() < wrap && word < words.length) {
				line += words[word] + " ";
				word++;
			}
			data.bot.notice(data.event.source, line);
			line = "";
		}
	}

	abstract public boolean match(String rawdata);

	abstract public void run(String rawdata, PassedData data);

}
