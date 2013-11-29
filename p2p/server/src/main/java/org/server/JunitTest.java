package org.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class JunitTest {

	@Test
	public void testMain() 
	{
		//Main main = new Main();
		Main.main(null);
	}
	
	@Test
	public void Server()
	{
		//Dit stuk faalt!
		Server test = new Server();
		test.start(6666);
		test.sendMessage("test");
	}

    @Test
    public void xmlParser()
    {
    	xmlParser parser = new xmlParser();
    	final ContainerSetXml containers;
    	try
    	{
    		containers = parser.parse("XML_files/xml1.xml");
    		for (ContainerXml c : containers.containers) {
            	System.out.println(c.id + " Owner Name: " + c.ownerName +  "\n");
    		}
    	}
    	catch(Exception ex)
    	{
    	}
    	
    }





}
