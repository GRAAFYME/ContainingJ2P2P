package org.server;

import org.protocol.*;
import org.protocol.Container;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


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
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private static ProtocolParser parser;

    public static void main(String[] args) throws Exception
    {
        parser = new ProtocolParser();
        Server server = new Server();
        server.start(6666);
        String a = "";
        //Create temp Protocol with 1 Container
        Protocol p = new Protocol();
        p.containers.add(new Container());

        for(int i = 0; ; i++)
        {
            Thread.sleep(1000);
             a += server.getMessages();
            server.sendMessage(parser.serialize(p));
        }
    }
    public String getMessages()
    {
        String message = "";

        try {
            while(reader.ready())
            {
                //Gotta love java... copied from stackoverflow
                //Reading these streams is hell
                java.util.Scanner s = new java.util.Scanner(reader).useDelimiter("\r\r");
                message = s.hasNext() ? s.next() : "";
                System.out.println(message);
                return message;
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return "";
        }

        return message;
    }

    public void sendMessage(String message)
    {
        try {
            //\r\r (2x linefeed) is our special "message has ended" sequence, since reading network streams
            //in java sucks we hack around it like this
            writer.write(message + "\r\r");
            writer.flush();
        }
        catch (Exception e) {
            System.out.println("Message" + message + "\n----------------\nGave exception:\n-----------\n" + e.getMessage());
        }
    }

    public boolean start(int port)
    {
        try {
            //Set the server up
            serverSocket = new ServerSocket(port);

            //Wait till someone connects; this is a blocking method!!!!!!!!
            clientSocket = serverSocket.accept();
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}


