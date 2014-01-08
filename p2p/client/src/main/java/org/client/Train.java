package org.client;

import com.jme3.scene.Spatial;

public class Train extends Vehicle
{
	public Train(String id, Spatial train) 
	{
		super(id, train);
		
		train.scale(5);
		train.setLocalTranslation(800, 260, 802);
	}

}
