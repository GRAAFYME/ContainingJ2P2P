package org.server;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        return filterWrongInstances(containers);
    }

    private ContainerSetXml filterWrongInstances(ContainerSetXml set)
    {
        //Makes changes to arguments
        List<ContainerXml> containers = set.containers;
        set.containers = removeNrDuplicates(containers);
        return set;
    }

    // 11124 duplicate containers(Nr) removed
    //Apparently these dups are allowed?
    //TODO: check if these dups are allowed
    private List<ContainerXml> removeNrDuplicates(List<ContainerXml> containers)
    {
        final List<ContainerXml> returnList = new ArrayList();
        final Set<Integer> set1 = new HashSet();

        for(ContainerXml c : containers)
        {
            if (set1.add(c.nr))
            {
                returnList.add(c);
            }
        }
        System.out.println(containers.size() - returnList.size() + " duplicate containers(Nr) removed");
        return returnList;
    }
}

