package net.mms_projects.irc.channel_bots.pb;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import com.jolbox.bonecp.BoneCP;

public class Table {

	public String name;

	protected BoneCP connectionPool;
	protected boolean initialized = false;

	private String createQuery;
	private boolean createNonExisting = false;

	public Table(BoneCP connectionPool, String name) {
		this.connectionPool = connectionPool;
		this.name = name;
	}

	public void initialize() {
		try {
			if ((!this.exists()) && (this.createNonExisting)) {
				Logger.getGlobal().info("Creating non existing table " + name);
				this.createTable();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.initialized = true;
	}

	public boolean exists() throws SQLException {
		Connection connection = this.getConnection();
		Statement statement = connection.createStatement();
		try {
			statement.execute("SELECT * FROM " + this.name);
		} catch (SQLException e) {
			if (e.getMessage().contains("no such table")) {
				return false;
			} else {
				throw e;
			}
		} finally {
			connection.close();
			statement.close();
		}
		return true;
	}

	protected void createTable(String query) throws SQLException {
		Statement statement = this.getConnection().createStatement();
		statement.execute(query);
	}

	protected void createTable() throws SQLException {
		this.createTable(this.createQuery);
	}

	protected void setCreateQuery(String query) {
		this.createQuery = query;
		this.createNonExisting = true;
	}

	public boolean isInitialized() {
		return this.initialized;
	}

	protected Connection getConnection() throws SQLException {
		return this.connectionPool.getConnection();
	}

}
