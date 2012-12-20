package net.mms_projects.irc.channel_bots.pb.commands;

import net.mms_projects.irc.channel_bots.Channel;
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
		super("add", "Adds a trigger", handler, parent);

		this.addSyntax(new CommandSyntax(new Text("trigger"), new Text("type",
				new String[] { "msg", "join" }), new Text("data")));

		this.addHelp();
	}

	@Override
	public void run(String rawdata, PassedData data) {
		Tokenizer tokens = new Tokenizer(rawdata, " ");
		if (tokens.numTok() < 6) {
			this.getHelp().showSyntax(data);
			return;
		}
		Channel channel = data.channelList.getChannelByName(tokens.getToken(3));
		if (channel == null) {
			this.reply(data, "Unknown channel " + tokens.getToken(3));
			return;
		}
		Trigger trigger = null;
		switch (tokens.getToken(4).toLowerCase()) {
		case "msg":
			trigger = new Msg();
			break;
		case "join":
			trigger = new Join();
			break;
		default:
			this.reply(data, "Unknown trigger type " + tokens.getToken(4) + ".");
			this.getHelp().showSyntax(data);
			return;
		}
		trigger.data = tokens.getToken("5-");
		data.tableTriggers.updateOrInsert(channel, trigger);
		data.bot.notice(data.event.source, String.format(
				"Added a trigger to channel %1$s. With data: %2$s",
				channel.name, trigger.data));
	}
}
