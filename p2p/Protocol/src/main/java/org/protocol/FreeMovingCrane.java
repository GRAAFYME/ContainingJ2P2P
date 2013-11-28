package org.protocol;

import javax.vecmath.Vector3f;


public class FreeMovingCrane extends Crane
{
	private float velocity;

	public FreeMovingCrane(Vector3f _location) 
	{
		super(_location);
	}
	
	@Override
	protected float velocity(){
		velocity = 3;
		return velocity;
	}
	
}
