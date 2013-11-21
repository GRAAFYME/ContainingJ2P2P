package org.client;

import java.io.IOException;

public class networkClient {
	
	public networkClient() throws IOException
	{
		try
		{
		com.jme3.network.Client myClient = com.jme3.network.Network.connectToServer("localhost", 6666);
		myClient.start();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
