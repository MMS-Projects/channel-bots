package net.mms_projects.irc.channel_bots.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.mms_projects.irc.channel_bots.Bot;
import net.mms_projects.irc.channel_bots.Channel;
import net.mms_projects.irc.channel_bots.ChannelList;
import net.mms_projects.irc.channel_bots.Plugin;
import net.mms_projects.irc.channel_bots.ServerList;
import net.mms_projects.irc.channel_bots.Socket;
import net.mms_projects.irc.channel_bots.UserList;
import net.mms_projects.irc.channel_bots.irc.Handler;
import net.mms_projects.irc.channel_bots.irc.commands.Join;
import net.mms_projects.irc.channel_bots.irc.commands.Notice;
import net.mms_projects.irc.channel_bots.irc.commands.Part;
import net.mms_projects.irc.channel_bots.irc.commands.PrivMsg;
import net.mms_projects.irc.channel_bots.irc.commands.Topic;
import net.mms_projects.irc.channel_bots.listeners.ChannelListener;
import net.mms_projects.irc.channel_bots.listeners.MessageListener;
import net.mms_projects.irc.channel_bots.managers.ServiceManager;
import net.mms_projects.irc.channel_bots.pb.Action;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.PassedData;
import net.mms_projects.irc.channel_bots.pb.Trigger;
import net.mms_projects.irc.channel_bots.pb.commands.Add;
import net.mms_projects.irc.channel_bots.pb.commands.Help;
import net.mms_projects.irc.channel_bots.pb.commands.Variable;
import net.mms_projects.irc.channel_bots.pb.tables.Actions;
import net.mms_projects.irc.channel_bots.pb.tables.Triggers;
import net.mms_projects.irc.channel_bots.pbl.Parser;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ProgrammableBots extends Plugin implements MessageListener, ChannelListener {
	public ServiceManager manager;
	public Bot pbot;
	public net.mms_projects.irc.channel_bots.pbl.Handler pblHandler;
	public Parser pblParser;

	private CommandHandler cmdHandler;
	private Triggers triggerTable;
	private Actions actionTable;

	public ProgrammableBots(Socket socket, Handler handler, UserList userList,
			ChannelList channelList, ServerList serverList) {
		super(socket, handler, userList, channelList, serverList);

		Logger.getGlobal().setLevel(Level.ALL);

		this.pblHandler = new net.mms_projects.irc.channel_bots.pbl.Handler();
		this.pblParser = new Parser(this.pblHandler);

		this.manager = new ServiceManager(socket, handler, userList,
				channelList, serverList);

		pbot = new Bot("PBot", "Programmable Bot interface", "P-Bot",
				"channel-bot.mms-projects.net");
		this.manager.newBot(pbot);
		pbot.join("#test");

		this.pblHandler.setVariable("internal.irc.me", pbot.nickname);

		cmdHandler = new CommandHandler();
		cmdHandler.addCommand(new Add(cmdHandler));
		cmdHandler.addCommand(new Variable(cmdHandler));
		cmdHandler.addCommand(new Help(cmdHandler));

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			e.printStackTrace();
		}

		BoneCP connectionPool = null;

		try {
			// setup the connection pool
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl("jdbc:sqlite:data/data.db");
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);
			config.setUsername("");
			config.setPassword("");
			config.setLogStatementsEnabled(true);
			connectionPool = new BoneCP(config);
		} catch (Exception e) {
			e.printStackTrace();
		}

		triggerTable = new Triggers(connectionPool);
		actionTable = new Actions(connectionPool);

		handler.addManager(this.manager);
		handler.addMessageListener(this);
		handler.addChannelListener(this);
	}

	public void tick() {
		super.tick();
	}

	@Override
	public void onPrivMsg(PrivMsg event) {
		if (event.target.equalsIgnoreCase("#test")) {
			Channel channel = this.channelList.getChannelByName(event.target);
			this.pblHandler.setVariable("internal.irc.chan", event.target);
			this.pblHandler.setVariable("internal.irc.nick", event.source);

			this.pbot.privMsg(event.target, this.pblParser.eval(event.text));

			List<Trigger> triggers = this.triggerTable
					.getChannelTriggers(channel);
			List<Action> actions = new ArrayList<Action>();
			for (Trigger trigger : triggers) {
				if (trigger.matches(event)) {
					actions = actionTable.getTriggerActions(trigger);
					for (Action action : actions) {
						this.pbot.privMsg(event.target, this.pblParser.eval(action.data));
					}
				}
			}
		}
		if (event.target.equalsIgnoreCase(this.pbot.nickname)) {
			boolean handled = this.cmdHandler
					.handle(event.text, new PassedData(this.userList,
							this.channelList, this.serverList, this.pbot,
							event, this.pblHandler));
			if (!handled) {
				this.pbot.notice(event.source, "No command match >:(");
			}
		}
	}

	@Override
	public void onNotice(Notice event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void userJoined(Join event) {
		for (String channelName : event.channels) {
			Channel channel = this.channelList.getChannelByName(channelName);
			this.pblHandler.setVariable("internal.irc.chan", channelName);
			this.pblHandler.setVariable("internal.irc.nick", event.nickname);
	
			List<Trigger> triggers = this.triggerTable
					.getChannelTriggers(channel);
			List<Action> actions = new ArrayList<Action>();
			for (Trigger trigger : triggers) {
				if (trigger.matches(event)) {
					actions = actionTable.getTriggerActions(trigger);
					for (Action action : actions) {
						this.pbot.privMsg(channelName, this.pblParser.eval(action.data));
					}
				}
			}
		}
	}

	@Override
	public void userLeft(Part event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void topicChanged(Topic event) {
		// TODO Auto-generated method stub
		
	}
}
