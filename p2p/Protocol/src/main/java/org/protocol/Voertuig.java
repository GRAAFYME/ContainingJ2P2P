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
	
	public Voertuig(Vector3f Loc)
	{
		this.Location = Loc;
	}
	
	public Vector3f GetLocation()
	{
		return Location;
	}
	
	public void SetLocation(Vector3f Loc)
	{
		Location = Loc;
	}
}
