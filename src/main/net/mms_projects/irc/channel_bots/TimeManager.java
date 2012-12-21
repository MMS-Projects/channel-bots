package net.mms_projects.irc.channel_bots;

import java.util.Date;

public class TimeManager {

	private long offset = 0;
	
	public TimeManager(long offset) {
		this.setOffset(offset);
	}
	
	public void setOffset(long offset) {
		this.offset = offset;
	}
	
	public Date getDate() {
		return new Date(System.currentTimeMillis() + this.offset);
	}
	
}
