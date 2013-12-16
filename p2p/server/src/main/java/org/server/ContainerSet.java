package org.server;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 20/11/13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name="recordset")
@XmlSeeAlso(Container.class)
public class ContainerSet {

    @XmlElement(name="record")
    List<Container> containers;



}
