package net.mms_projects.irc.channel_bots;

public class Bot {
	public String nickname;
	public String hostname;
	public String username;
	public String realname;
	
	public Bot (String nickname, String realname, String username, 
			String hostname) {
		this.nickname = nickname;
		this.hostname = hostname;
		this.username = username;
		this.realname = realname;
	}
}
