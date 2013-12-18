package org.client;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Containers extends Node
{
	protected Spatial container;
	
	private String id;
	
	public Containers(String id, Vector3f position, Spatial container)
	{
		super(id);
		
		this.id = id;
		this.container = container.clone();	
		this.container.rotate(0,90*FastMath.DEG_TO_RAD,0);
		
		this.attachChild(this.container);
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void detachContainer()
	{
		this.detachChild(this.container);
	}
}
