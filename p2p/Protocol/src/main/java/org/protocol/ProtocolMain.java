package org.protocol;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

//Temporary test class
public class ProtocolMain {
    public static void main(String args[]) {

        Protocol p = new Protocol();
        p.containers.add(new Container());
        String xmlOutput = "";

        try {
            //---Generate Xml
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            JAXBContext jaxbContext = JAXBContext.newInstance(Protocol.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Pretty printing enabled
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(p, stream);
            xmlOutput = new String(stream.toByteArray(), Charset.defaultCharset());

            System.out.println(xmlOutput);
            //---Create Class from Xml
            Protocol p2 = new Protocol();
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            InputStream stream2 = new ByteArrayInputStream(xmlOutput.getBytes("UTF-8"));

            p2 = (Protocol) jaxbUnmarshaller.unmarshal(stream2);
            System.out.println(p2.containers.get(0).d);

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println(xmlOutput);
    }
}
