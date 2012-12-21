package net.mms_projects.irc.channel_bots;

public class Topic {

	public String nickname;
	public int created;
	public String text;
	
	public Topic(String nickname, int created, String text) {
		this.nickname = nickname;
		this.created = created;
		this.text = text;
	}
	
}
