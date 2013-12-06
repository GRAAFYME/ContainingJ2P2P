package org.client;

import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Vector3f;

public class Crane implements MotionPathListener
{
	//TODO: Add methods here for crane movement/motionpath
	//Vectors come in the specific classes
	
	MotionPath path;
	Vector3f posCrane, posHook, posSlider, destination;
	
	public Crane()
	{
		moveCrane(new Vector3f(10,0,0), 0.01f);
	}
	
	public void moveCrane(Vector3f des, float sp)
	{
		path.addWayPoint(des);
		System.out.println(des);
	}
	
	public void moveHook(Vector3f des, float sp)
	{
		
	}
	
	public void moveSlider(Vector3f des, float sp)
	{
		
	}

	public void onWayPointReach(MotionEvent arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
