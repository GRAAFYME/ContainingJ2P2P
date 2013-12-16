package org.server;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 21/11/13
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */
public class oldMain {
    public static void main( String[] args )
    {
        //Create the server and start listening for connections

        Server server = new Server();
        server.start(6666);

        xmlParser parser = new xmlParser();
        ContainerSetXml containers;
        try {
            long time = System.nanoTime();
            containers = parser.parse("data/xml1.xml");
            System.out.println("It took" + (System.nanoTime() - time) + "ns to parse the xml file");
            //for (Container c : containers)
            //    System.out.println("("+c.id+")Name: " + c.name + " is carrying " + c.contentName + " which is " + c.contentDanger);
        }
        catch (Exception e){
            System.out.println("EORR!");
        }

        System.out.println( "Hello World!" );

        while(true)
        {
            //blabla.update((float) speed);
            //String message = server.getMessagess();

        }
    }

}
