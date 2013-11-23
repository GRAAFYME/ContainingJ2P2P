package org.protocol;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 22/11/13
 * Time: 15:01
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class Protocol {

    @XmlElement
    public boolean isZip;
    @XmlElement
    public List<Container> containers;
    
    public Protocol()
    {
        containers = new ArrayList<Container>();
    }
    
    public List<Container> getContainers()
    {
    	return containers;
    }

}
