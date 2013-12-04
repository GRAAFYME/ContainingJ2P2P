package org.server;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.protocol.Container;
import org.protocol.Protocol;
import org.protocol.ProtocolParser;
import org.protocol.Statistics;

import javax.vecmath.Point3d;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
    private FTPClient ftpClient;
    private Statistics statistics;

    public static void main(String[] args) throws Exception
    {
        parser = new ProtocolParser();
        Server server = new Server();
        Protocol p = null;
        p = new Protocol();
        p.getContainers().add(new Container());
        p.getContainers().get(0).location = new Point3d(50, 130, 50);
        server.start(6666);
        
        while(true)
        {
            Thread.sleep(100);
            //Add tiny amount
            p.getContainers().get(0).location.add(new Point3d(1f, 0f, 0f));
            server.sendMessage(parser.serialize(p));
        }
    }

    public Server()
    {
        ftpClient = new FTPClient();
        ftpClient.setBufferSize(1024 * 1024);
    }

    public void loginToStatisticsServer(String host, String name, String password)
    {
        try {
            boolean success;
            ftpClient.connect(host);
            success = ftpClient.login(name, password);

            System.out.println("Ftp login success!");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    public void uploadStatistics()
    {
        if(ftpClient == null)
        {
            System.out.println("Please login first!");
            return;
        }
        try
        {
            InputStream stream = new ByteArrayInputStream(statistics.serializeJson().getBytes());
            ftpClient.setFileType(FTP.ASCII_FILE_TYPE);//only for txt file ACII mode, for rest binary mode
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile("httpdocs/uploads/statistics.json", stream);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
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
            statistics = new Statistics(1, 2, 3, 4, 5);
            //Set the server up
            //serverSocket = new ServerSocket(port);
	        //parser = new ProtocolParser();
	        //Protocol protocol = null;

            //Wait till someone connects; this is a blocking method!!!!!!!!
            //clientSocket = serverSocket.accept();
            //Does this do anything at all?
                //clientSocket.setKeepAlive(true);
                //clientSocket.setSoTimeout(500);
                //writer = new PrintWriter(clientSocket.getOutputStream(), true);
                //reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                //protocol = new Protocol();
                //protocol.getContainers().add(new Container());
                //protocol.getContainers().get(0).location = new Point3d(50, 130, 50);

                //Infinite loop, this should go in a new thread
	        int counter = 0;
            while(true)
            {
                Thread.sleep(100);
                //Add tiny amount
                //protocol.getContainers().get(0).location.add(new Point3d(1f, 0f, 0f));
                //sendMessage(parser.serialize(protocol));

                if (counter % 15 == 0)
                {
                    statistics.trein++;
                    statistics.trein = statistics.trein % 15; //cycle 0-15
                    uploadStatistics();
                }
                counter++;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}


