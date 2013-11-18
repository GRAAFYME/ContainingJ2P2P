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
System.out.println("aa");
        }
        catch (Exception e){
            System.out.println("eorr!");
        }

        System.out.println( "Hello World!" );
    }
}
