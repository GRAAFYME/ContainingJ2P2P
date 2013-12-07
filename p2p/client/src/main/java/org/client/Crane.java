package org.client;

import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public abstract class Crane extends Node implements MotionPathListener
{
	private MotionPath pathCrane;
	private MotionPath pathSlider;
	private MotionPath pathHook;
	private MotionEvent motionControl;
	
	protected Spatial crane;
	protected Spatial slider;
	protected Spatial hook;
	protected Node hookNode = new Node();
	protected Node sliderNode = new Node();
	
	private String id;
	
	private Vector3f position, posCrane, posHook, posSlider, destination;
	
	public Crane(String id, Vector3f position, Spatial crane, Spatial slider, Spatial hook)
	{
		super(id);
		
		this.id = id;
		this.position = position;
		this.crane = crane.clone();
		this.slider = slider.clone();
		this.hook = hook.clone();
		
		this.attachChild(this.crane);
		
		hookNode.attachChild(this.hook);
		sliderNode.attachChild(hookNode);
		sliderNode.attachChild(this.slider);
		
		this.attachChild(this.sliderNode);
		
		pathCrane.setCycle(false);
		pathSlider.setCycle(false);
		pathHook.setCycle(false);
		
		motionControl = new MotionEvent();
	}
	
	public void update(float tpf)
	{
		crane.updateLogicalState(tpf);
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public Vector3f getPosition()
	{
		return this.position;
	}
	
	public void moveCrane(Vector3f des, float sp)
	{
		pathCrane.addWayPoint(des);		
		//System.out.println("Number of WayPoints: " + path.getNbWayPoints());
		
		pathCrane.removeWayPoint(des);
	}
	
	public void moveHook(Vector3f des, float sp)
	{
		
	}
	
	public void moveSlider(Vector3f des, float sp)
	{
		
	}

	public void onWayPointReach(MotionEvent control, int wayPointIndex) {
		// TODO Auto-generated method stub
		if (pathCrane.getNbWayPoints() == wayPointIndex + 1) 
		{
			System.out.println(control.getSpatial().getName() + " has finished moving. ");
		} 
		else 
		{
			System.out.println(control.getSpatial().getName() + " has reached way point " + wayPointIndex);
		}
	}
}
