package org.server;


import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ContainerXml
{
    @XmlAttribute
    String id;

    @XmlPath("aankomst/datum/d/text()")
    int arrivalDay;
    @XmlPath("aankomst/datum/m/text()")
    int arrivalMonth;
    @XmlPath("aankomst/datum/j/text()")
    int arrivalYear;
    @XmlPath("aankomst/tijd/van/text()")
    String arrivalTill;
    @XmlPath("aankomst/tijd/tot/text()")
    String arrivalFrom;

    @XmlPath("vertrek/datum/d/text()")
    int leaveDay;
    @XmlPath("vertrek/datum/m/text()")
    int leaveMonth;
    @XmlPath("vertrek/datum/j/text()")
    int leaveYear;
    @XmlPath("vertrek/tijd/van/text()")
    String leaveFrom;
    @XmlPath("vertrek/tijd/tot/text()")
    String leaveTill;
    @XmlPath("aankomst/positie/x/text()")
    int arrivalPosX;
    @XmlPath("aankomst/positie/y/text()")
    int arrivalPosY;
    @XmlPath("aankomst/positie/z/text()")
    int arrivalPosZ;
    @XmlPath("eigenaar/naam/text()")
    String ownerName;
    @XmlPath("eigenaar/containernr/text()")
    int nr;
    @XmlPath("aankomst/soort_vervoer/text()")
    String arrivalTransportType;
    @XmlPath("vertrek/soort_vervoer/text()")
    String leaveTransportType;
    @XmlPath("aankomst/bedrijf/text()")
    String arrivalCompany;
    @XmlPath("vertrek/bedrijf/text()")
    String leaveCompany;
    @XmlPath("gewicht/leeg/text()")
    int emptyWeight;
    @XmlPath("gewicht/inhoud/text()")
    int fullWeight;
    @XmlPath("inhoud/naam/text()")
    String contentName;
    @XmlPath("inhoud/soort/text()")
    String contentType;
    @XmlPath("inhoud/gevaar/text()")
    String dangerType;
    @XmlElement(name="ISO")
    String iso;
}
