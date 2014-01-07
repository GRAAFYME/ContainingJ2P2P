package org.server;


import org.junit.*;

import static org.junit.Assert.fail;

public class JunitTest {

	Main main = new Main();
	
	@Test
	public void Server()
	{
		Server test = new Server();
		try
		{
		test.start(6666);
		//test.sendMessage("test");
		}
		catch(Exception ex)
		{
			fail("server niet on!");
		}
		
		test.restart(6666);
		
		try
		{
		test.stop();
		//test.sendMessage("test");
		}
		catch(Exception ex)
		{
			fail("server niet on!");
		}
	}

    @Test
    public void xmlParser()
    {
    	xmlParser parser = new xmlParser();
    	final ContainerSetXml containers;
    	try
    	{
    		containers = parser.load("data/xml7.xml");
    		for (ContainerXml c : containers.containers) {
            	System.out.println(c.id + " Owner Name: " + c.ownerName +  "\n");
    		}
    	}
    	catch(Exception ex)
    	{
    		fail("Test failed file not found.");
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

