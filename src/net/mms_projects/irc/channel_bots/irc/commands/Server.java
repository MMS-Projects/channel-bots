package net.mms_projects.irc.channel_bots.irc.commands;

import java.util.regex.Matcher;

import net.mms_projects.irc.channel_bots.irc.Command;

public class Server extends Command {

	public String name;
	public int hopcount;
	public String protocolVersion;
	public String protocolFlags;
	public String serverNumeric;
	public String server;
	public String description;
	
	public Server() {
		//SERVER server.name 1 :Uprotocolversion-protocolflags-servernumeric server description
		//SERVER dev.mms-projects.net 1 :U2310-FhinXOoE-100 MMS-Projects Development server
		super("SERVER (.+) (\\d+) :(.+)-(.+)-(.+) (.+) (.+)", "");
	}

	@Override
	public void parse(String rawdata) {
		Matcher matcher = this.match(rawdata);
		System.out.println("BLABLABLA" + rawdata);
		if (matcher.find()) {
			this.name = matcher.group(1);
			this.hopcount = Integer.parseInt(matcher.group(2));
			this.protocolVersion = matcher.group(3);
			this.protocolFlags = matcher.group(4);
			this.serverNumeric = matcher.group(5);
			this.server = matcher.group(6);
			this.description = matcher.group(7);
			System.out.println("Name: " + name);
			System.out.println("Hopcount: " + hopcount);
			System.out.println("proVersion: " + protocolVersion);
			System.out.println("proFlags: " + protocolFlags);
			System.out.println("serverNumeric: " + serverNumeric);
			System.out.println("Server: " + server);
			System.out.println("Description: " + description);
		}
	}

	@Override
	public String build() {
		return "";
	}

}
