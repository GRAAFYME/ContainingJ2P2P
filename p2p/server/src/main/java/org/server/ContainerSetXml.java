package org.server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@XmlRootElement(name = "recordset")
@XmlSeeAlso(ContainerXml.class)
public class ContainerSetXml {

	@XmlElement(name = "record")
	List<ContainerXml> containers;
}
