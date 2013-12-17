package org.protocol;

import javax.vecmath.Vector3f;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 22/11/13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class Crane 
{
	protected Vector3f Location;
	private boolean isBusy = false;
	private float velocity;
	
	public Crane(Vector3f _location)
	{
		this.Location = _location;
	}
	
	protected float velocity(){
		velocity = 1;
		return velocity;
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
