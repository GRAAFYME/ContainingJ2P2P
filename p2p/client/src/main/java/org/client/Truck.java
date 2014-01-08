package org.client;

import com.jme3.math.FastMath;
import com.jme3.scene.Spatial;

public class Truck extends Vehicle 
{

	public Truck(String id, Spatial truck) 
	{
		super(id, truck);
		
		this.scale(2);
		this.rotate(0, 90*FastMath.DEG_TO_RAD, 0);
	}

}
