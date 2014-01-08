package org.client;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public abstract class Vehicle extends Node 
{
	private String id;
	private Vector3f position;
	
	public Vehicle(String id, Spatial vehicle)
	{
		this.attachChild(vehicle);
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
			{
				position = new Vector3f(270+(x*40), 255, 20);
			}
		}
		return position;
	}
}
