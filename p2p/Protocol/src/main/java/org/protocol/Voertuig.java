package org.protocol;

import javax.vecmath.Vector3f;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 22/11/13
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public class Voertuig 
{
	private Vector3f Location;
	
	public Voertuig(Vector3f _location)
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
