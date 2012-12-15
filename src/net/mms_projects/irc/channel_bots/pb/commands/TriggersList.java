package net.mms_projects.irc.channel_bots.pb.commands;

import java.util.List;

import net.mms_projects.irc.channel_bots.Channel;
import net.mms_projects.irc.channel_bots.Tokenizer;
import net.mms_projects.irc.channel_bots.pb.Command;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.PassedData;
import net.mms_projects.irc.channel_bots.pb.Trigger;

public class TriggersList extends Command {

	public TriggersList(CommandHandler handler, Command parent) {
		super("list", "Lists all the triggers", handler, parent);
		
		this.addHelp();
	}

	@Override
	public boolean match(String rawdata) {
		return rawdata.startsWith(this.getFullCommand());
	}

	@Override
	public void run(String rawdata, PassedData data) {
		Tokenizer tokens = new Tokenizer(rawdata, " ");
		Channel channel = data.channelList.getChannelByName(tokens.getToken(3));
		if (channel == null) {
			this.reply(data, "Unknown channel " + tokens.getToken(3));
			return;
		}
		List<Trigger> triggers = data.tableTriggers.getChannelTriggers(channel);
		this.reply(data, String.format("%1$-10s", "type") + "data");
		for (Trigger trigger : triggers) {
			this.reply(data, "  " + String.format("%1$-10s", trigger.id) + trigger.data);
		}
		if (triggers.isEmpty()) {
			data.bot.notice(data.event.source, "No triggers made yet");
		}
	}

}
