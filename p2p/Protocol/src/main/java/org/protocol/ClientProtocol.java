package org.protocol;

import java.util.List;

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
	
	@XmlElement
	public List<Point3d> possibleRoutes;
	
	@XmlElement
	public List<Float> lengthRoutes;
	
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
	
	public void setRoutes(List<Point3d> routes, List<Float> lengths)
	{
		this.possibleRoutes = routes;
		this.lengthRoutes = lengths;
	}

}
