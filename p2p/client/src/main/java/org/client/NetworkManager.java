package org.client;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 21/11/13
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
class NetworkManager 
{
	public NetworkManager() throws IOException
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
