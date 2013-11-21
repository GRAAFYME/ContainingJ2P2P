package org.server;

/**
 * Hello world!
 *
 */

/*
 * Authors
 * Joshua Bergsma
 * Remco de Bruin
 * Yme van der Graaf
 * Jeffrey Harders
 * Arjen Pander
 * Melinda de Roo
 * */

public class Server 
{
    public static void main( String[] args )
    {
        xmlParser parser = new xmlParser();
        ContainerSet containers;
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
    }
}
