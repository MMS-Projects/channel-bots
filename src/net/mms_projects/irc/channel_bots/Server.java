package net.mms_projects.irc.channel_bots;

import net.mms_projects.irc.channel_bots.irc.commands.ServerIntroduce;

public class Server {
	public String server;
	public int hopCount;
	public String description;
	
	// Optional options
	public int protocolVersion;
	public String protocolFlags;
	public int serverNumeric;
	public String serverName;
	
	public Server () {
	}

	public static Server createFromServerIntroduced(ServerIntroduce event) {
		Server s = new Server();
		s.server = event.server;
		s.hopCount = event.hopCount;
		s.description = event.description;
		s.protocolVersion = event.protocolVersion;
		s.protocolFlags = event.protocolFlags;
		s.serverNumeric = event.serverNumeric;
		s.serverName = event.serverName;
		return s;
	}
}
