package org.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
    private BufferedReader reader;

	public networkClient(int port)
	{
		try
		{
            socket = new Socket("127.0.0.1", 6666);
            //socket.setSoTimeout(5);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

    //Give me a String and I'll pass it to the server
    public void sendMessage(String message)
    {
        writer.write(message);
        //TODO: remove this?(flush) Not sure if we need this, probably not.
        writer.flush();
    }

    public String getMessages()
    {
        String message = "";

        try {
            while(reader.ready())
            {
                //Gotta love java... copied from stackoverflow
                //Reading these streams is hell
                java.util.Scanner s = new java.util.Scanner(reader).useDelimiter("\r\r");
                message = s.hasNext() ? s.next() : "";
                return message;
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return "";
        }

        return message;
    }
    
}
