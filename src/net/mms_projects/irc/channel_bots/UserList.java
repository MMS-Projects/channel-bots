package net.mms_projects.irc.channel_bots;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("serial")
public class UserList extends CopyOnWriteArrayList<User> {

	public void updateUserHost(String nickname, String hostname) {
		for (Iterator<User> iterator = this.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			if (user.nickname.equals(nickname)) {
				user.hostname = hostname;
			}
		}
	}
	
	public void updateUserAwayStatus(String nickname, String awayStatus) {
		for (Iterator<User> iterator = this.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			if (user.nickname.equals(nickname)) {
				user.awayStatus = awayStatus;
			}
		}
	}
	
	public void removeUser (String nickname) {
		for (Iterator<User> iterator = this.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			if (user.nickname.equals(nickname)) {
				remove(user);
				break;
			}
		}
	}
}
