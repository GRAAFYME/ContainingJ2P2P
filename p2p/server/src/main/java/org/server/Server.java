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
        try {
        parser.parse("data/xml1.xml");
        }
        catch (Exception e){
            System.out.println("eorr!");
        }

        System.out.println( "Hello World!" );
    }
}
