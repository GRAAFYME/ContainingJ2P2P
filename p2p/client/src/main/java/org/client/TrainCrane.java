package org.client;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class TrainCrane extends Crane
{

	public TrainCrane(String id, Vector3f position, Spatial crane, Spatial slider, Spatial hook) 
	{
		super(id, position, crane, slider, hook);
		
		this.crane.scale(3);
		this.slider.scale(3);
		this.hook.scale(3);
	}

}
