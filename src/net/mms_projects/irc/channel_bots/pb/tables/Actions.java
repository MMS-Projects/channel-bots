package net.mms_projects.irc.channel_bots.pb.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.mms_projects.irc.channel_bots.pb.Action;
import net.mms_projects.irc.channel_bots.pb.Table;
import net.mms_projects.irc.channel_bots.pb.Trigger;

import com.jolbox.bonecp.BoneCP;

public class Actions extends Table {

	public Actions(BoneCP connectionPool) {
		super(connectionPool, "pb_actions");
		this.setCreateQuery("CREATE TABLE \"pb_actions\" ("
				+ "\"id\" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "\"trigger_id\" INTEGER, " + "\"type\" TEXT, "
				+ "\"data\" TEXT" + ");");
		this.initialize();
	}

	public List<Action> getTriggerActions(Trigger trigger) {
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

		List<Action> actions = new ArrayList<Action>();

		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = this.getConnection().prepareStatement(
					"SELECT * FROM pb_actions WHERE trigger_id = ?");
			statement.setInt(1, trigger.id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				System.out
						.println("Action data:" + resultSet.getString("data"));
				actions.add(Action.create(resultSet.getInt("id"),
						resultSet.getInt("trigger_id"),
						resultSet.getString("type"),
						resultSet.getString("data")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
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
		return actions;
	}

}
