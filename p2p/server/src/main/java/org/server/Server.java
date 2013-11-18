package org.server;

import java.util.List;

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
        try {
        List<Container> containers = parser.parse("data/xml1.xml");

            for (Container c : containers)
                System.out.println("("+c.id+")Name: " + c.name + " is carrying " + c.contentName + " which is " + c.contentDanger);
        }
        catch (Exception e){
            System.out.println("EORR!");
        }

        System.out.println( "Hello World!" );
    }
}
