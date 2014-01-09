package org.protocol;

import javax.vecmath.Vector3f;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 22/11/13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class Crane extends ContainerCarrier
{
	public Vector3f Location;
    public Vector3f coordinates;
    public boolean available;
	//private boolean isBusy = false;
	
	public Crane(Vector3f _location, int index)
	{
		this.Location = _location;
        this.Location.x = index;
        this.available = true;

	}
	
	protected Vector3f GetLocation()
	{
		return Location;
	}
	
	//Only RailCrane can move
	protected void SetLocation(Vector3f _location)
	{
		Location = _location;
	}
	
}
