package org.server;

import static org.junit.Assert.*;

import org.junit.Test;

public class JunitTest {

	@Test
	public void testMain() 
	{
		Main main = new Main();
		
	}
	
	@Test
	public void Server()
	{
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
    	}
    	catch(Exception ex)
    	{
    	}
    	
    }





}
