package net.mms_projects.irc.channel_bots;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("serial")
public class ServerList extends CopyOnWriteArrayList<Server> {
	public void updateServerSynced(String serverName, boolean synced) {
		for (Iterator<Server> iterator = this.iterator(); iterator.hasNext();) {
			Server server = iterator.next();
			if (server.server.equals(serverName)) {
				server.synced = synced;
			}
		}
	}
}