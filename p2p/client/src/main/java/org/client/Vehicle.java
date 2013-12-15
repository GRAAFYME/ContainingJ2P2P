package org.client;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public abstract class Vehicle extends Node 
{
	public Vehicle(Spatial vehicle)
	{
		this.attachChild(vehicle);
	}
}
