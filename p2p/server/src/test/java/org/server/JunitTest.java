package org.server;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JunitTest {

	Main main = new Main();
	
	@Test
	public void Server()
	{
		//Dit stuk faalt denk ik?
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
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}



}

