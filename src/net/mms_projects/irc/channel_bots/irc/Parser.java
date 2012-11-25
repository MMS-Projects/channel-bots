package net.mms_projects.irc.channel_bots.irc;

import java.util.ArrayList;

public class Parser {

	private ArrayList<Command> commands = new ArrayList<Command>();
	
	public void addCommand(Command command) {
		this.commands.add(command);
	}
	
	public Command parse(String rawdata) {
		for (Command command : this.commands) {
			if (command.checkMatch(rawdata)) {
				try {
					command = command.getClass().newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				command.parse(rawdata);
				return command;
			}
		}
		return null;
	}

}
