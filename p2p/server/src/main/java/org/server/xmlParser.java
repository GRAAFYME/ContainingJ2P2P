package org.server;


import org.protocol.Container;
import org.protocol.Vehicle;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class xmlParser
{
    public static void main(String[] Args)
    {
        xmlParser parser = new xmlParser();
        ContainerSetXml set = null;

        try {
            set = parser.load("C:/Users/Remco/Desktop/xml7.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PriorityQueue<Vehicle> vehicles = parser.parse(set);
        System.out.println(vehicles.peek().containers.size() + ", vehicle #1 contains this many containers");
    }

    public ContainerSetXml load(String path) throws FileNotFoundException
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

    public PriorityQueue<Vehicle> parse(ContainerSetXml containerSet)
    {
        //PriorityQueue<Protocol> messages = new PriorityQueue<Protocol>();
        HashMap<String, Vehicle> map = new HashMap<String, Vehicle>();


        //TODO: test this
        for(ContainerXml c : containerSet.containers)
        {
            String uniqueTransportArrivalKey = c.arrivalTransportType + c.arrivalCompany + c.arrivalDay
                                       + c.arrivalFrom + c.arrivalMonth + c.arrivalTill + c.arrivalYear;
            Vehicle vehicle = map.get(uniqueTransportArrivalKey);

            //Not yet added, add now
            if(vehicle == null)
            {
                Vehicle newVehicle = new Vehicle();
                map.put(uniqueTransportArrivalKey, newVehicle);
                vehicle.containers = new ArrayList<Container>();
            }
            else
            {
                System.out.print("alelkerf  wesd1!!!!!!11111111111");
            }

            vehicle.containers.add(containerXmlToContainer(c));
        }

        PriorityQueue<Vehicle> queue = new PriorityQueue<Vehicle>(2, new Comparator<Vehicle>() {
            public int compare(Vehicle v1, Vehicle v2) {
                // compare n1 and n2
                Container c1 = v1.containers.get(0);
                Container c2 = v2.containers.get(0);

                int comparison1 = Integer.compare(c1.arrivalDay, c2.arrivalDay);
                int comparison2 = Integer.compare(c1.arrivalMonth, c2.arrivalMonth);
                int comparison3 = Integer.compare(c1.arrivalYear, c2.arrivalYear);

                int comparison4 = c1.arrivalFrom.compareTo(c2.arrivalFrom);

                if(comparison1 >= 0 && comparison2 >= 0 && comparison2 >= 0)
                    return comparison4;
                else
                    return -1;
            }});

        queue.addAll(map.values());
        return queue;
    }


    public Container containerXmlToContainer(ContainerXml container)
    {
        Container c = new Container();

        c.id = container.id;
        c.arrivalDay = container.arrivalDay;
        c.arrivalMonth = container.arrivalMonth;
        c.arrivalYear = container.arrivalYear;
        c.arrivalTill = container.arrivalTill;
        c.arrivalFrom = container.arrivalFrom;
        c.leaveDay = container.leaveDay;
        c.leaveMonth = container.leaveMonth;
        c.leaveYear = container.leaveYear;
        c.leaveFrom = container.leaveFrom;
        c.leaveTill = container.leaveTill;
        c.arrivalPosX = container.arrivalPosX;
        c.arrivalPosY = container.arrivalPosY;
        c.arrivalPosZ = container.arrivalPosZ;
        c.ownerName = container.ownerName;
        c.nr = container.nr;
        c.arrivalTransportType = container.arrivalTransportType;
        c.leaveTransportType = container.leaveTransportType;
        c.arrivalCompany = container.arrivalCompany;
        c.leaveCompany = container.leaveCompany;
        c.emptyWeight = container.emptyWeight;
        c.fullWeight = container.fullWeight;
        c.contentName = container.contentName;
        c.contentType = container.contentType;
        c.dangerType = container.dangerType;
        c.iso = container.iso;

        return c;
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

