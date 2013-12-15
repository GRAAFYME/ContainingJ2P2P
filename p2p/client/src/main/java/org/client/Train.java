package org.client;

import com.jme3.scene.Spatial;

public class Train extends Vehicle
{
	public Train(Spatial train) 
	{
		super(train);
		
		train.scale(5);
		train.setLocalTranslation(800, 260, 802);
	}

}
