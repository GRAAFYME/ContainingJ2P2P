package org.protocol;

import javax.vecmath.Vector3f;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 22/11/13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class Kraan 
{
	protected Vector3f Location;
	private boolean isBusy = false;
	
	public Kraan(Vector3f _location)
	{
		this.Location = _location;
	}
	
	protected Vector3f GetLocation()
	{
		return Location;
	}
	
	//Only RailKraan can move !?
	protected void SetLocation(Vector3f _location)
	{
		Location = _location;
	}
	
}
