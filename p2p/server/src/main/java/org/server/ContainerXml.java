package org.server;


import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ContainerXml
{
    @XmlAttribute
    public String id;

    @XmlPath("aankomst/datum/d/text()")
    public int arrivalDay;
    @XmlPath("aankomst/datum/m/text()")
    public int arrivalMonth;
    @XmlPath("aankomst/datum/j/text()")
    public int arrivalYear;
    @XmlPath("aankomst/tijd/van/text()")
    public String arrivalTill;
    @XmlPath("aankomst/tijd/tot/text()")
    public String arrivalFrom;

    @XmlPath("vertrek/datum/d/text()")
    public int leaveDay;
    @XmlPath("vertrek/datum/m/text()")
    public int leaveMonth;
    @XmlPath("vertrek/datum/j/text()")
    public int leaveYear;
    @XmlPath("vertrek/tijd/van/text()")
    public String leaveFrom;
    @XmlPath("vertrek/tijd/tot/text()")
    public String leaveTill;
    @XmlPath("aankomst/positie/x/text()")
    public int arrivalPosX;
    @XmlPath("aankomst/positie/y/text()")
    public int arrivalPosY;
    @XmlPath("aankomst/positie/z/text()")
    public int arrivalPosZ;
    @XmlPath("eigenaar/naam/text()")
    public String ownerName;
    @XmlPath("eigenaar/containernr/text()")
    public int nr;
    @XmlPath("aankomst/soort_vervoer/text()")
    public String arrivalTransportType;
    @XmlPath("vertrek/soort_vervoer/text()")
    public String leaveTransportType;
    @XmlPath("aankomst/bedrijf/text()")
    public String arrivalCompany;
    @XmlPath("vertrek/bedrijf/text()")
    public String leaveCompany;
    @XmlPath("gewicht/leeg/text()")
    public int emptyWeight;
    @XmlPath("gewicht/inhoud/text()")
    public int fullWeight;
    @XmlPath("inhoud/naam/text()")
    public String contentName;
    @XmlPath("inhoud/soort/text()")
    public String contentType;
    @XmlPath("inhoud/gevaar/text()")
    public String dangerType;
    @XmlElement(name="ISO")
    public String iso;
}
