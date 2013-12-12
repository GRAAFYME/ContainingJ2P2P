package org.client;

import java.util.HashMap;
import java.util.Map;

import com.jme3.math.Vector3f;

public class Storage 
{
	public Map<String,Map<String, Vector3f>> storageSpots = new HashMap<String,Map<String,Vector3f>>();
	private Map<String, Vector3f> spots = new HashMap<String, Vector3f>();
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
					spots.put(String.valueOf(counter+1), spot);
					counter++;
				}
			}
		}
		
		for(int i = 1; i <=20; i++)
		{
			storageSpots.put(String.valueOf(i), spots);
		}
	}
}
