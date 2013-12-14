package org.client;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class TruckCrane extends Crane
{  
    public TruckCrane(String id, Vector3f position, Spatial crane, Spatial slider, Spatial hook)
    {
    	super(id, position, crane, slider, hook);
    	
		this.crane.scale(2f);
		this.slider.scale(2f);
		this.hook.scale(2f);
		
		this.hook.rotate(0,90*FastMath.DEG_TO_RAD,0);
    }
}
