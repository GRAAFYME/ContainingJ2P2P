package org.protocol;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class ContainerCarrier {
    @XmlElement
    public List<Container> containers;

    @XmlTransient
    public boolean available = true;

    @XmlTransient
    public Calendar availableAgainDate;

    public ContainerCarrier() { containers = new ArrayList<>(); }
}
