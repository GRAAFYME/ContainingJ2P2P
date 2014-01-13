package org.client;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Containers extends BatchNode
{
	protected Spatial container;	
	private String id;
	
	public Containers(String id, Spatial container, boolean last)
	{
		super(id);
		
		this.id = id;
		this.container = container.clone();
		
		if(last == true)
		{
			batch();
			System.out.println("Batched");
		}
		
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