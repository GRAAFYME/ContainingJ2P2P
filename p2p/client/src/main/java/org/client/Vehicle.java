package org.client;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public abstract class Vehicle extends Node 
{
	private String id;
	private Vector3f position;
	private Spatial vehicle;
	private Spatial container;
	
	public Vehicle(String id, Spatial vehicle)
	{
		this.vehicle = vehicle.clone();
		this.attachChild(this.vehicle);
	}
	
	public String getId()
	{
		return id;
	}
	
	public Vector3f setLocation(String vehicle, float x, float y, float z)
	{
		switch(vehicle)
		{
			case "vrachtauto":
				this.position = new Vector3f(249+(x*40), 257, 50);
				break;
			case "zeeschip":
				this.position = new Vector3f(-730, 200, 680);
				break;
			case "trein":
				this.position = new Vector3f(800, 260, 802);
				break;
			case "binnenvaartsschip":
				this.position = new Vector3f(400,255,800);
				break;
		}
		return position;
	}
	
	public Spatial getContainer()
	{
		return container;
	}
	
	public void setContainer(Spatial cont)
	{
		container = cont.clone();
		this.attachChild(this.container);
	}
}
