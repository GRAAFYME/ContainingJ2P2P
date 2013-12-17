package org.protocol;

import javax.vecmath.Vector3f;
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
	private Vector3f Location;
    public List<Container> containers;

    public Vehicle() {}

	public Vehicle(Vector3f _location)
	{
		this.Location = _location;
	}
	
	protected Vector3f GetLocation()
	{
		return Location;
	}
	
	protected void SetLocation(Vector3f _location)
	{
		Location = _location;
	}
}
