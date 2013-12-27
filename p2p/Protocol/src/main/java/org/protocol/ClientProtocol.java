package org.protocol;

import javax.vecmath.Point3d;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClientProtocol 
{
	@XmlElement
	public String idAGV;
	
	@XmlElement
	public String idCrane;
	
	@XmlElement
	public Point3d locationAGV;
	
	@XmlElement
	public Point3d locationCrane;
	
	public ClientProtocol()
	{
	}
	
	public String getIDAGV()
	{
		return idAGV;
	}
	
	public String getIDCrane()
	{
		return idCrane;
	}
	
	public Point3d getLocationAGV()
	{
		return locationAGV;
	}
	
	public void setLocationAGV(Point3d location)
	{
		this.locationAGV = location;
	}
	
	public Point3d getLocationCrane()
	{
		return locationCrane;
	}
	
	public void setLocationCrane(Point3d location)
	{
		this.locationCrane = location;
	}

}
