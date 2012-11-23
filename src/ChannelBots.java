import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;

public class ChannelBots {

	static public void main(String[] args) {
		Socket socket = new Socket();
		socket.write("PASS :ServicesTest");
		socket.write("SERVER channels.mms-projects.net 1 :Channels services");
		while (true) {
			String line = socket.read();
			if (line.split(" ")[0].equalsIgnoreCase("PING")) {
				socket.write("PONG :" + line.split(":")[1]);
			}
		}
		

	}

}
