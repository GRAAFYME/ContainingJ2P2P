package org.protocol;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 22/11/13
 * Time: 23:29
 * To change this template use File | Settings | File Templates.
 */
public class ProtocolParser {
    public String serialize(Object protocol)
    {
        try {
            OutputStream stream = new ByteArrayOutputStream();

            JAXBContext jaxbContext = JAXBContext.newInstance(Protocol.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(protocol, stream);

            //Not a proper way they say
            return stream.toString();
         }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Protocol deserialize(String packet)
    {
        InputStream stream = new ByteArrayInputStream(packet.getBytes());
        Protocol p = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Protocol.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            p = (Protocol)jaxbUnmarshaller.unmarshal(stream);
            return p;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
