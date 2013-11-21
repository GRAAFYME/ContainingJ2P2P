package org.server;

import java.io.BufferedReader;
import java.io.IOException;
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

    public String getMessages() throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //Gotta love java... copied from stackoverflow
        java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public boolean start(int port)
    {
        try {
            //Set the server up
            serverSocket = new ServerSocket(port);
            //Wait till someone connects; this is a blocking method!!!!!!!!
            clientSocket = serverSocket.accept();
            //random debug stuff
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.print("YO");


        }
        catch (Exception e) {
            return false;
        }

        return true;
    }
}


