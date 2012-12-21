package net.mms_projects.irc.channel_bots.pb.commands;

import net.mms_projects.irc.channel_bots.Channel;
import net.mms_projects.irc.channel_bots.Messages;
import net.mms_projects.irc.channel_bots.Tokenizer;
import net.mms_projects.irc.channel_bots.pb.Command;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.CommandSyntax;
import net.mms_projects.irc.channel_bots.pb.PassedData;
import net.mms_projects.irc.channel_bots.pb.Trigger;
import net.mms_projects.irc.channel_bots.pb.command_arguments.Text;
import net.mms_projects.irc.channel_bots.pb.triggers.Join;
import net.mms_projects.irc.channel_bots.pb.triggers.Msg;

public class TriggersAdd extends Command {

	public TriggersAdd(CommandHandler handler, Command parent) {
		super("add", "Adds a trigger", handler, parent); //$NON-NLS-1$ //$NON-NLS-2$

		this.addSyntax(new CommandSyntax(new Text("trigger"), new Text("type", //$NON-NLS-1$ //$NON-NLS-2$
				new String[] { "msg", "join" }), new Text("data"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		this.addHelp();
	}

	@Override
	public void run(String rawdata, PassedData data) {
		Tokenizer tokens = new Tokenizer(rawdata, " "); //$NON-NLS-1$
		if (tokens.numTok() < 6) {
			this.getHelp().showSyntax(data);
			return;
		}
		Channel channel = data.channelList.getChannelByName(tokens.getToken(3));
		if (channel == null) {
			this.reply(data, "Unknown channel " + tokens.getToken(3)); //$NON-NLS-1$
			return;
		}
		Trigger trigger = null;
		switch (tokens.getToken(4).toLowerCase()) {
		case "msg": //$NON-NLS-1$
			trigger = new Msg();
			break;
		case "join": //$NON-NLS-1$
			trigger = new Join();
			break;
		default:
			this.reply(data, "Unknown trigger type " + tokens.getToken(4) + "."); //$NON-NLS-1$ //$NON-NLS-2$
			this.getHelp().showSyntax(data);
			return;
		}
		trigger.data = tokens.getToken("5-"); //$NON-NLS-1$
		data.tableTriggers.updateOrInsert(channel, trigger);
		data.bot.notice(data.event.source, String.format(
				Messages.getString("pb.command.triggers_add.added_a_trigger"), //$NON-NLS-1$
				channel.name, trigger.data));
	}
}
