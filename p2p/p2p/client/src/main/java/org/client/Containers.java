package org.client;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Containers extends Node
{
	protected Spatial container;
	protected Vector3f position;
	
	public Containers(String id, Vector3f position, Spatial container)
	{
		super(id);
		
		this.container = container.clone();
		this.position = position;
		
		this.container.rotate(0,90*FastMath.DEG_TO_RAD,0);
		
		this.attachChild(this.container);
	}
}
