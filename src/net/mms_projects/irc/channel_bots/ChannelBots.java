package net.mms_projects.irc.channel_bots;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.text.Format;
import java.util.Formatter;
import java.util.Scanner;

import net.mms_projects.irc.channel_bots.irc.Command;
import net.mms_projects.irc.channel_bots.irc.Parser;
import net.mms_projects.irc.channel_bots.irc.commands.EOS;
import net.mms_projects.irc.channel_bots.irc.commands.NetInfo;
import net.mms_projects.irc.channel_bots.irc.commands.Pass;
import net.mms_projects.irc.channel_bots.irc.commands.Ping;

public class ChannelBots {

	static public void main(String[] args) {
		Pass pass = new Pass();
		pass.password = "ServicesTest";
		
		NetInfo netInfo = new NetInfo();
		netInfo.maxGlobal = 10;
		netInfo.currentTime = (int) (System.currentTimeMillis() / 1000);
		netInfo.protocolVersion = 0;
		netInfo.cloakHash = "*";
		netInfo.networkName = "MMS-Projects IRC";
		
		Socket socket = new Socket();
		socket.write(pass.toString());
		socket.write("SERVER channels.mms-projects.net 1 :Channels services");
		socket.write("NICK ChannelBot 1 1 ChannelBot channel-bot.mms-projects.net channels.mms-projects.net 1 :Channel Bot");
		socket.write(netInfo.toString());
		
		Parser parser = new Parser();
		parser.addCommand(new Pass());
		parser.addCommand(new EOS());
		parser.addCommand(new NetInfo());
		parser.addCommand(new Ping());
		
		while (true) {
			String line = socket.read();
			Command parsed = parser.parse(line);
			System.out.println("Parsed: " + parsed);
			
			if (parsed instanceof Ping) {
				Ping ping = (Ping) parsed;
				socket.write("PONG :" + ping.token);
			}
		}
		

	}

}
