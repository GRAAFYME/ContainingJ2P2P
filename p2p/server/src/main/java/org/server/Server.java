package org.server;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.protocol.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;


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

public class Server implements Runnable
{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private static ProtocolParser parser;
    private FTPClient ftpClient;
    private Statistics statistics;
    private Thread thread;
    private boolean isRunning;

    public boolean enableHeartbeat;
    public int heartBeatTimeout;

    public static void main(String[] args) throws Exception
    {
        parser = new ProtocolParser();
        Server server = new Server();
        ServerProtocol p = null;
        p = new ServerProtocol();
        p.getContainers().add(new Container());
        //p.getContainers().get(0).location = new Point3d(50, 130, 50);
        server.start(6666);
        
        while(true)
        {
            Thread.sleep(100);
            //Add tiny amount
            //p.getContainers().get(0).location.add(new Point3d(1f, 0f, 0f));
            server.sendMessage(parser.serialize(p));
        }
    }

    public Server()
    {
        ftpClient = new FTPClient();
        ftpClient.setBufferSize(1024 * 1024);
        thread = new Thread();
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

    //This launches a new thread!
    public void start(int port)
    {
        try {
            if(thread.isAlive() == true)
            {
                System.out.println("Server is already running!\n Either stop it first or press restart.");
                return;
            }

            System.out.println("Server is being started");
            statistics = new Statistics(1, 2, 3, 4, 5);
            //Set the server up
            serverSocket = new ServerSocket(port);
            parser = new ProtocolParser();
            ServerProtocol protocol = null;

            protocol = new ServerProtocol();
            protocol.getContainers().add(new Container());
            //protocol.getContainers().get(0).location = new Point3d(50, 130, 50);

            //This will run this.run() in a new thread
            thread = new Thread(this);
            thread.start();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    //Thread will periodically check if isRunning is set to false, if so it terminates itself
    public void stop()
    {
        System.out.println("Server is being stopped");
        isRunning = false;
    }

    public void restart(int port)
    {
        int counter = 0;

        stop();
        //Wait till the thread noticed it should stop and exited

        while(thread.isAlive())
        {
                if(counter == 10)
            {
                thread.destroy();
                System.out.println("Waited 1 second, server is probably blocked by socket.accept(), force stop!");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
        }

        start(port);
    }

    @Override
    public void run()
        {
            boolean ftpDebugOnly = true;
            try {
                String message = "";
                xmlParser xmlparser = new xmlParser();
                ContainerSetXml containerset =  xmlparser.load("data/xml1.xml");
                PriorityQueue<Vehicle> queue = xmlparser.parse(containerset);
                Simulator simulator = new Simulator(queue);

                isRunning = true;
                //Wait till someone connects; this is a blocking method!!!!!!!!
                System.out.println("Waiting till a client connects, I (Server server) won't do anything untill this happens!!!");
                if(!ftpDebugOnly)
                {
                clientSocket = serverSocket.accept();
                    //Does this do anything at all?
                    clientSocket.setSoTimeout(500);
                    writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                }
                int counter = 0;
                long lastIterationTime = System.nanoTime();
                while(true)
                {
                    /////Things that exit this server thread go here
                    //This is the proper way to exit a thread, thread.stop()/destroy()/etc() is bad?
                    if(isRunning == false)
                        stop();

                    //Heartbeat check, If client hasn't responded for a while, presume disconnection
                    if(enableHeartbeat && System.nanoTime() - lastIterationTime > heartBeatTimeout * 1000000)//10^-6
                    {
                        //exit from this thread
                        return;
                    }


                    ////Server stuff goes down here
                    //Check if the client said anything
                    if(!ftpDebugOnly)
                        message = getMessages();

                    //Has the client said anything? if so, reset timer.
                    if(message != "")
                    {
                        lastIterationTime = System.nanoTime();
                    }

                    //1 second has passed
                    //These lines actually transmit code to the client
                    ServerProtocol protocol = simulator.update(1);
                    if(protocol != null)
                    {
                        sendMessage(parser.serialize(protocol));
                    }




                    Thread.sleep(100);
                    //Add tiny amount

                    if (counter % 100 == 0)
                    {
                        if(!ftpDebugOnly) sendMessage(parser.serialize(queue.poll()));
                    }

                    if (counter % 15 == 0)
                    {
                        statistics.trein++;
                        statistics.trein = statistics.trein % 15; //cycle 0-15
                        uploadStatistics();
                    }
                    counter++;
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
}

