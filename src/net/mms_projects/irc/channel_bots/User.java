package net.mms_projects.irc.channel_bots;

import net.mms_projects.irc.channel_bots.irc.commands.NickIntroduce;

public class User {

	public String nickname;
	public String username;
	public String hostname;
	public String realname;
	public String awayStatus;
	
	static public User createFromNickIntroduce(NickIntroduce event) {
		User user = new User();
		user.nickname = event.nickname;
		user.username = event.username;
		user.hostname = event.hostname;
		user.realname = event.realname;
		return user;
	}
	
}
