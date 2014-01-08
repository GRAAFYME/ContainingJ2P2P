package org.client;

import com.jme3.math.FastMath;
import com.jme3.scene.Spatial;

public class SeaShip extends Vehicle
{

	public SeaShip(String id, Spatial seaShip) 
	{
		super(id, seaShip);
		
		seaShip.setLocalTranslation(-730, 200, 680);
		seaShip.scale(2);
		seaShip.rotate(0,-4*FastMath.DEG_TO_RAD,0);
	}
}
