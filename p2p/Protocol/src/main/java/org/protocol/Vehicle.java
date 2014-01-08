package org.protocol;

import javax.vecmath.Vector3f;
import javax.xml.bind.annotation.XmlElement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 22/11/13
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public class Vehicle
{
    @XmlElement
	private Vector3f Location;
    @XmlElement
    public List<Container> containers;
    @XmlElement
    public Vector3f location; //truck: only consider values x: 0-19
    //public String className;

    public Vehicle() {}

	public Vehicle(Vector3f _location, String className)
	{
		this.Location = _location;
        //this.className = className;
	}
	
	protected Vector3f GetLocation()
	{
		return Location;
	}
	
	protected void SetLocation(Vector3f _location)
	{
		Location = _location;
	}


    public String getClassName()
    {
        //Is there a container? yes: just copy its arrival date
        return (containers.size() == 0) ? null : containers.get(0).arrivalTransportType;
    }
    public Calendar getArrivalDate()
    {
        //Is there a container? yes: just copy its arrival date
        return (containers.size() == 0) ? null : containers.get(0).getArrivalDate();
    }

    public GregorianCalendar getLeaveDate()
    {
        //Is there a container? yes: just copy its arrival date
        return (containers.size() == 0) ? null : containers.get(0).getLeaveDate();
    }
}
