package org.server;

//"Shared" class

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Container {
    @XmlElement
    int x, y, z;
}
