package org.client;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

//This server class is a horrible mess of conflicting names
//
//TODO: Possibly change Client.java's class name
//TODO: Fix names in networkClient.java (including classname itself?)
//

//Why don't we use jMonkey's networking code?
//It requires us to create the server in jmonkey as well
//because it excpects the server to expect to receive the exact Message : AbstractMessage
//object, we don't want jMonkey deps in our server
public class networkClient {

	private Socket socket;
	private PrintWriter writer;
	private InputStreamReader reader;
	private Scanner scanner;

	public networkClient(int port) {
		try {
			socket = new Socket("127.0.0.1", 6666);
			// socket.setSoTimeout(5);
			writer = new PrintWriter(socket.getOutputStream(), true);
			reader = new InputStreamReader(socket.getInputStream());
			scanner = new Scanner(reader);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Server knows the magic word for a heartbeat
	public void SendHeartbeat() {
		sendMessage("hb");
	}

	// Give me a String and I'll pass it to the server
	public void sendMessage(String message) {
		try {
			// checkError() returns true when disconnected, handy!
			if (socket == null || socket.isConnected() == false
					|| message == null || message == "")
				return;
			// \r\r (2x linefeed) is our special "message has ended" sequence,
			// since reading network streams
			// in java sucks we hack around it like this
			writer.print(message + "\r\r");
			writer.flush();
		} catch (Exception e) {
			System.out.println("Message" + message
					+ "\n----------------\nGave exception:\n-----------\n"
					+ e.getMessage());
		}
	}

	public String getMessages() {
		String message = "";

		try {
			while (reader.ready()) {
				// Gotta love java... copied from stackoverflow
				message = scanner.useDelimiter("\r\r").next();
				return message;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}

		return message;
	}

}
