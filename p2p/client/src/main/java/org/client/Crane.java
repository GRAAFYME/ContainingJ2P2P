package org.client;

import com.jme3.animation.LoopMode;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public abstract class Crane extends Node implements MotionPathListener
{
	private final float SPEED = 0.5f;
	private MotionPath pathCrane = new MotionPath();
	private MotionPath pathSlider = new MotionPath();
	private MotionPath pathHook = new MotionPath();
	private MotionEvent craneControl;
	private MotionEvent sliderControl;
	private MotionEvent hookControl;
	
	protected Spatial crane;
	protected Spatial slider;
	protected Spatial hook;
	protected Node hookNode = new Node();
	protected Node sliderNode = new Node();
	
	private String id;
	
	private Vector3f position;
	
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
		
		craneControl = new MotionEvent(this, pathCrane, SPEED, LoopMode.DontLoop);
		sliderControl = new MotionEvent(this.sliderNode, pathSlider, SPEED, LoopMode.DontLoop);
		hookControl = new MotionEvent(this.hookNode, pathHook, SPEED, LoopMode.DontLoop);
	}
	
	public void update(float tpf)
	{
		this.crane.setLocalTranslation(this.getLocalTranslation());
		this.slider.setLocalTranslation(sliderNode.getLocalTranslation());
		this.hook.setLocalTranslation(hookNode.getLocalTranslation());
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
		Vector3f startPosition, desPosition;
		startPosition = crane.getLocalTranslation();
		desPosition = des;
		
		if(startPosition.distance(desPosition) > 0)
		{
			pathCrane.clearWayPoints();
			
			pathCrane.addWayPoint(startPosition);
			pathCrane.addWayPoint(desPosition);	
			
			craneControl.play();
		}
		//System.out.println("Number of WayPoints: " + path.getNbWayPoints());
	}
	
	public void moveSlider(Vector3f des, float sp)
	{
		Vector3f startPosition, desPosition;
		startPosition = sliderNode.getLocalTranslation();
		desPosition = des;
		
		if(startPosition.distance(desPosition) > 0)
		{
			pathSlider.clearWayPoints();
			
			pathSlider.addWayPoint(startPosition);
			pathSlider.addWayPoint(desPosition);
			
			sliderControl.play();
		}
		//System.out.println("Number of WayPoints: " + path.getNbWayPoints());
	}
	
	public void moveHook(Vector3f des, float sp)
	{
		Vector3f startPosition, desPosition;
		startPosition = hookNode.getLocalTranslation();
		desPosition = des;
		
		if(startPosition.distance(desPosition) > 0)
		{
			pathHook.clearWayPoints();
			
			pathHook.addWayPoint(startPosition);
			pathHook.addWayPoint(desPosition);
			
			hookControl.play();
		}
		//System.out.println("Number of WayPoints: " + path.getNbWayPoints());
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
