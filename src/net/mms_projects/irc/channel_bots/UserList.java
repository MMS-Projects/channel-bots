package net.mms_projects.irc.channel_bots;

import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class UserList extends ArrayList<User> {

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
	
	public void updateNickname(String oldNickname, String newNickname) {
		for (Iterator<User> iterator = this.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			if (user.nickname.equals(oldNickname)) {
				user.nickname = newNickname;
			}
		}
	}
	
}
