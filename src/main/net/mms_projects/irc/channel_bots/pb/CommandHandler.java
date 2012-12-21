package net.mms_projects.irc.channel_bots.pb;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

	protected List<Command> commands = new ArrayList<Command>();

	public void addCommand(Command command) {
		this.commands.add(command);
	}

	public boolean hasCommands() {
		return !this.commands.isEmpty();
	}
	
	final public void showCommands(PassedData data, int maxDepth) {
		this.showCommands("  ", data, 0, maxDepth);
	}

	public void showCommands(String prefix, PassedData data, int depth,
			int maxDepth) {
		if (depth >= maxDepth) {
			return;
		}
		for (Command command : this.commands) {
			if (!command.showInList) {
				continue;
			}
			data.bot.notice(data.event.source,
					prefix + String.format("%1$-10s", command.command)
							+ " - " + command.shortDescription);
			command.showCommands(prefix + "-  ", data, depth + 1, maxDepth);
		}
	}

	public boolean handle(String rawdata, PassedData data) {
		Command cmd = null;
		for (Command command : this.commands) {
			if (command.match(rawdata)) {
				cmd = command;
			}
		}
		if (cmd != null) {
			cmd.run(rawdata, data);
			return true;
		} else {
			return false;
		}
	}

}
