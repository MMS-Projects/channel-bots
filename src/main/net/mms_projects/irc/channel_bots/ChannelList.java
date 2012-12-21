package net.mms_projects.irc.channel_bots;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("serial")
public class ChannelList extends CopyOnWriteArrayList<Channel> {

	public void updateTopic(String channel, Topic topic) {
		for (Iterator<Channel> iterator = this.iterator(); iterator.hasNext();) {
			Channel entity = iterator.next();
			if (entity.name.equals(channel)) {
				entity.topic = topic;
				break;
			}
		}
	}
	
	public void removeChannel(String name) {
		for (Iterator<Channel> iterator = this.iterator(); iterator.hasNext();) {
			Channel entity = iterator.next();
			if (entity.name.equalsIgnoreCase(name)) {
				remove(entity);
				break;
			}
		}
	}

	public Channel getChannelByName(String name) {
		for (Iterator<Channel> iterator = this.iterator(); iterator.hasNext();) {
			Channel entity = iterator.next();
			if (entity.name.equalsIgnoreCase(name)) {
				return entity;
			}
		}
		return null;
	}

}
