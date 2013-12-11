package org.client;

import java.util.HashMap;
import java.util.Map;

import com.jme3.math.Vector3f;

public class Storage 
{
	public Map<String,Vector3f> storageSpots = new HashMap<String,Vector3f>();
	private int counter = 0;
	
	public Storage()
	{
		create();
	}
	
	private void create()
	{
		for(int y = 0; y < 6; y++)
		{
			for(int x = 0; x < 6; x++)
			{
				for(int z = 0; z < 46; z++)
				{
					Vector3f spot = new Vector3f(x,y,z);
					storageSpots.put(String.valueOf(counter), spot);
					counter++;
				}
			}
		}
		for (Map.Entry entry : storageSpots.entrySet()) {
		    System.out.println(entry.getKey() + ", " + entry.getValue());
		}
	}
}
