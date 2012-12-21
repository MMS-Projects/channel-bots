package net.mms_projects.irc.channel_bots.pb.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.mms_projects.irc.channel_bots.Channel;
import net.mms_projects.irc.channel_bots.pb.Table;
import net.mms_projects.irc.channel_bots.pb.Trigger;

import com.jolbox.bonecp.BoneCP;

public class Triggers extends Table {

	public Triggers(BoneCP connectionPool) {
		super(connectionPool, "pb_triggers");
		this.setCreateQuery("CREATE TABLE \"pb_triggers\" ("
				+ "    \"id\" INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "    \"type\" TEXT," + "    \"data\" TEXT,"
				+ "    \"channel\" TEXT" + ");");
		this.initialize();
	}

	public List<Trigger> getChannelTriggers(Channel channel) {
		if (!this.isInitialized()) {
			new Exception("Table not initialized").printStackTrace();
		}
		try {
			if (!this.exists()) {
				new Exception("Table not existing").printStackTrace();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List<Trigger> triggers = new ArrayList<Trigger>();

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = this.connectionPool.getConnection();
			statement = connection.prepareStatement(
					"SELECT * FROM pb_triggers WHERE channel LIKE ?");
			statement.setString(1, channel.name);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				System.out.println("Trigger data:"
						+ resultSet.getString("data"));
				triggers.add(Trigger.createTrigger(resultSet.getInt("id"),
						resultSet.getString("type"),
						resultSet.getString("data")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return triggers;
	}

}
