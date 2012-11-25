package net.mms_projects.irc.channel_bots.irc;

import java.util.ArrayList;

import net.mms_projects.irc.channel_bots.irc.commands.UnknownCommand;

public class Parser {

	private ArrayList<Command> commands = new ArrayList<Command>();
	
	public void addCommand(Command command) {
		this.commands.add(command);
	}
	
	public Command parse(String rawdata) {
		Command commandReturned = new UnknownCommand();
		for (Command command : this.commands) {
			if (command.checkMatch(rawdata)) {
				try {
					commandReturned = command.getClass().newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				commandReturned.parse(rawdata);
				return command;
			}
		}
		commandReturned.parse(rawdata);
		return commandReturned;
	}

}
