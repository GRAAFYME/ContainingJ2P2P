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
		
		for(int i = 0; i < 24; i++)
		{
			for(int y = 0; y < 6; y++)
			{
				for(int x = 0; x < 6; x++)
				{
					for(int z = 0; z < 35; z++)
					{
						Vector3f spot = new Vector3f((i*20)-8.75f+(x*2.5f),(y*2.5f),715-7.5f-(z*15));
						spots.put(String.valueOf(counter+1), spot);
						counter++;
					}
				}
			}
			storageSpots.put(String.valueOf(i), spots);
		}
	}
}
