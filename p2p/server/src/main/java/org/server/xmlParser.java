package org.server;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class xmlParser
{
    public ContainerSetXml parse(String path) throws FileNotFoundException
    {
        List<ContainerXml> containerList = new ArrayList<ContainerXml>();
        ContainerSetXml containers = null;


        try {
            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(ContainerSetXml.class);
            System.out.println(jaxbContext.getClass());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
           containers = (ContainerSetXml) jaxbUnmarshaller.unmarshal(file);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return containers;
    }
}

