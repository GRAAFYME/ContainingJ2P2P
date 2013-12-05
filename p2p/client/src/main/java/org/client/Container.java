package org.client;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;

public class Container 
{	
	AssetManager assetManager;
	Vector3f position;
	
	public Container(AssetManager assetManager, Vector3f position)
	{
		this.assetManager = assetManager;
		this.position = position;
	}
}
