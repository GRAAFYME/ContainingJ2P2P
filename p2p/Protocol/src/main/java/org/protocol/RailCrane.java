package org.protocol;

import javax.vecmath.Vector3f;


public class RailCrane extends Crane
{
	private float velocity;
	
	public RailCrane(Vector3f _location) 
	{
		super(_location);
	}
	
	@Override
	protected float velocity(){
		velocity = 2;
		return velocity;
	}
	
}
