package net.mms_projects.irc.channel_bots.plugins;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import net.mms_projects.irc.channel_bots.Bot;
import net.mms_projects.irc.channel_bots.ChannelList;
import net.mms_projects.irc.channel_bots.Plugin;
import net.mms_projects.irc.channel_bots.ServerList;
import net.mms_projects.irc.channel_bots.Socket;
import net.mms_projects.irc.channel_bots.UserList;
import net.mms_projects.irc.channel_bots.irc.Handler;
import net.mms_projects.irc.channel_bots.irc.commands.Notice;
import net.mms_projects.irc.channel_bots.irc.commands.PrivMsg;
import net.mms_projects.irc.channel_bots.listeners.MessageListener;
import net.mms_projects.irc.channel_bots.managers.ServiceManager;
import net.mms_projects.irc.channel_bots.pb.CommandHandler;
import net.mms_projects.irc.channel_bots.pb.PassedData;
import net.mms_projects.irc.channel_bots.pb.commands.Add;
import net.mms_projects.irc.channel_bots.pb.commands.Help;
import net.mms_projects.irc.channel_bots.pb.commands.Variable;
import net.mms_projects.irc.channel_bots.pbl.Parser;

public class ProgrammableBots extends Plugin implements MessageListener {
	public ServiceManager manager;
	public Bot pbot;
	public net.mms_projects.irc.channel_bots.pbl.Handler pblHandler;
	public Parser pblParser;

	Connection connection = null;
	ResultSet resultSet = null;
	Statement statement = null;
	private CommandHandler cmdHandler;

	public ProgrammableBots(Socket socket, Handler handler, UserList userList,
			ChannelList channelList, ServerList serverList) {
		super(socket, handler, userList, channelList, serverList);

		this.pblHandler = new net.mms_projects.irc.channel_bots.pbl.Handler();
		this.pblParser = new Parser(this.pblHandler);

		this.manager = new ServiceManager(socket, handler, userList,
				channelList, serverList);
		handler.addManager(this.manager);
		handler.addMessageListener(this);
		
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
			connection = DriverManager
					.getConnection("jdbc:sqlite:data/data.db");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM pb_triggers");
			while (resultSet.next()) {
				System.out.println("Trigger data:"
						+ resultSet.getString("data"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void tick() {
		super.tick();
	}

	@Override
	public void onPrivMsg(PrivMsg event) {
		if (event.target.equalsIgnoreCase("#test")) {
			this.pblHandler.setVariable("internal.irc.chan", event.target);
			this.pblHandler.setVariable("internal.irc.nick", event.source);

			this.pbot.privMsg(event.target, this.pblParser.eval(event.text));
		}
		if (event.target.equalsIgnoreCase(this.pbot.nickname)) {
			boolean handled = this.cmdHandler.handle(event.text,
					new PassedData(this.userList, this.channelList,
							this.serverList, this.pbot, event));
			if (!handled) {
				this.pbot.notice(event.source, "No command match >:(");
			}
		}
	}

	@Override
	public void onNotice(Notice event) {
		// TODO Auto-generated method stub
	}
}
