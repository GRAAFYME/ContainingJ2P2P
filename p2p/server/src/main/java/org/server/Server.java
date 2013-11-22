package org.server;

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
    private PrintWriter socketStream;

    public String getMessages()
    {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //Gotta love java... copied from stackoverflow
            java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void sendMessage(String message)
    {
        try {
        socketStream.print(message);
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
            socketStream = new PrintWriter(clientSocket.getOutputStream(), true);
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}


