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
import net.mms_projects.irc.channel_bots.managers.ServiceManager;

public class ProgrammableBots extends Plugin {
	public ServiceManager manager;
	public Bot pbot;

	Connection connection = null;
	ResultSet resultSet = null;
	Statement statement = null;

	public ProgrammableBots(Socket socket, Handler handler, UserList userList,
			ChannelList channelList, ServerList serverList) {
		super(socket, handler, userList, channelList, serverList);

		this.manager = new ServiceManager(socket, handler, userList,
				channelList, serverList);
		handler.addManager(this.manager);

		pbot = new Bot("PBot", "Programmable Bot interface", "P-Bot",
				"channel-bot.mms-projects.net");
		this.manager.newBot(pbot);
		pbot.join("#test");

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager
					.getConnection("jdbc:sqlite:data/data.db");
			statement = connection.createStatement();
			resultSet = statement
					.executeQuery("SELECT * FROM pb_triggers");
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
}
