import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;


public class Socket {

	java.net.Socket connection = null;
	BufferedReader reader = null;
	BufferedWriter writer = null;
	
	public Socket() {      
        try {
            connection = new java.net.Socket();
            connection.connect(new InetSocketAddress("192.168.2.20", 7070));
            writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
            System.exit(1);
        }
	}
	
	public void close() {
		try {
			reader.close();
			writer.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(String text) {
		try {
			System.out.println("Write: " + text);
			writer.write(text + "\r\n" + "\r\n");
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String read() {
		try {
			String line = reader.readLine();
			if (line != null) {
				System.out.println("Read: " + line);
				return line;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
