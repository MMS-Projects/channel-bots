package net.mms_projects.irc.channel_bots;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("serial")
public class ChannelList extends CopyOnWriteArrayList<Channel> {

	public void updateTopic(String channel, net.mms_projects.irc.channel_bots.Topic topic) {
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
			if (entity.name.equals(name)) {
				remove(entity);
				break;
			}
		}
	}

}
