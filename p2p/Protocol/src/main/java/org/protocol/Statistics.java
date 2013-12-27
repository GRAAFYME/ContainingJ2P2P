package org.protocol;

import org.apache.commons.net.ftp.FTPClient;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 25/11/13
 * Time: 15:00
 * To change this template use File | Settings | File Templates.
 */



public class Statistics {

    public int trein;
    public int vrachtauto;
    public int zeeSchip;
    public int binnenvaartSchip;
    public int storage;

//For serialized: Ignore dis attribute
    private FTPClient ftpClient;

    public Statistics(int trein, int vrachtauto, int zeeSchip, int binnenvaartSchip, int storage)
    {
        this.trein            = trein;
        this.vrachtauto       = vrachtauto;
        this.zeeSchip         = zeeSchip;
        this.binnenvaartSchip = binnenvaartSchip;
        this.storage          = storage;
    }


    public static void main(String[] args)
    {
        Statistics stat = new Statistics(1, 2, 3, 4, 5);
    }

    public String serializeJson()
    {
        try {
            //String result = "";
            //MessageFormat messageFormat = new MessageFormat(
            String result =
                      "{"
                    + "	\"cols\":"
                    + "			["
                    + "				{\"id\": \"Platforms\", \"label\": \"Platforms\", \"type\": \"string\"},"
                    + "				{\"id\": \"Containers\", \"label\": \"Containers\", \"type\": \"number\"}"
                    + "			],"
                    + "	\"rows\":"
                    + "			["
                    + "				{\"c\":[{\"v\": \"Trein\"}, {\"v\": "+ trein +"}]},"
                    + "				{\"c\":[{\"v\": \"Vrachtauto\"}, {\"v\":"+ vrachtauto +"}]},"
                    + "				{\"c\":[{\"v\": \"Zeeschip\"}, {\"v\": "+ zeeSchip +"}]},"
                    + "				{\"c\":[{\"v\": \"Binnenvaartschip\"}, {\"v\": "+ binnenvaartSchip +"}]},"
                    + "				{\"c\":[{\"v\": \"Opslag\"}, {\"v\": "+ storage +"}]}"
                    + "			]"
                    + "}";

            return result;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ServerProtocol deserialize(String packet)
    {
        InputStream stream = new ByteArrayInputStream(packet.getBytes());
        ServerProtocol p = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ServerProtocol.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            p = (ServerProtocol)jaxbUnmarshaller.unmarshal(stream);
            return p;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
