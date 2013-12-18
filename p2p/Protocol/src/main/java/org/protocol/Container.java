package org.protocol;

import javax.vecmath.Point3d;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 22/11/13
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
public class Container {
    @XmlAttribute
    public String id;

    @XmlElement
    public int arrivalDay;
    @XmlElement
    public int arrivalMonth;
    @XmlElement
    public int arrivalYear;
    @XmlElement
    public String arrivalTill;
    @XmlElement
    public String arrivalFrom;

    @XmlElement
    public int leaveDay;
    @XmlElement
    public int leaveMonth;
    @XmlElement
    public int leaveYear;
    @XmlElement
    public String leaveFrom;
    @XmlElement
    public String leaveTill;
    @XmlElement
    public int arrivalPosX;
    @XmlElement
    public int arrivalPosY;
    @XmlElement
    public int arrivalPosZ;
    @XmlElement
    public String ownerName;
    @XmlElement
    public int nr;
    @XmlElement
    public String arrivalTransportType;
    @XmlElement
    public String leaveTransportType;
    @XmlElement
    public String arrivalCompany;
    @XmlElement
    public String leaveCompany;
    @XmlElement
    public int emptyWeight;
    @XmlElement
    public int fullWeight;
    @XmlElement
    public String contentName;
    @XmlElement
    public String contentType;
    @XmlElement
    public String dangerType;
    @XmlElement(name="ISO")
    public String iso;
    
    public Point3d location = new Point3d(0,0,0);

    public Point3d getLocation()
    {
    	return location;
    }
}
