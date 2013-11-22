package org.client;

import com.jme3.network.AbstractMessage;
import com.jme3.network.MessageListener;
import com.jme3.network.serializing.Serializable;

//This server class is a horrible mess of conflicting names
//
//TODO: Possibly change Client.java's class name
//TODO: Fix names in networkClient.java (including classname itself?)
//
public class networkClient implements MessageListener<com.jme3.network.Client> {

    @Serializable
    private class Message extends AbstractMessage {
        private String hello;       // custom message data
        public Message(String s) { hello = s; } // custom constructor
    }

	private com.jme3.network.Client myClient;

	public networkClient(int port)
	{
		try
		{
		myClient = com.jme3.network.Network.connectToServer("localhost", port);
		myClient.start();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

    //Give me a String and I'll pass it to the server
    public void sendMessage(String message)
    {
        Message messagePacket = new Message("message");
        myClient.send(messagePacket);
    }

    //OOooooooh callback method, thx jmonkey!
    @Override
    public void messageReceived(com.jme3.network.Client client, com.jme3.network.Message message)
    {
        //I don't know how this exactly works, I presume jmonkey does some threading magic
        //behind our backs
        System.out.println();
        //TODO: possibly store this in some Stream object (or String if lazy)
        //        So we can call getMessages() whenever we want/ this might be more hassle
        //        however might resolve multithreading difficulties, if any
    }
}
