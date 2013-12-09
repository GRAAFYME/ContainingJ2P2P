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
		this.setLocalTranslation(this.getLocalTranslation());
		this.sliderNode.setLocalTranslation(sliderNode.getLocalTranslation());
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public Vector3f getPosition()
	{
		return this.position;
	}
	
	public void animation(String id, int action, final Vector3f[] des, final float sp)
	{
		switch(action)
		{
			case 1:
			{
				//Vehicle to AGV
//				moveCrane(des[0], sp);
//				moveSlider(des[1], sp);
//				moveHook(des[2], sp);
//				moveSlider(des[3], sp);
//				moveHook(des[4], sp);
			}
			case 2:
			{
				//AGV to storage
				moveCrane(des[0], sp);
				pathCrane.addListener(new MotionPathListener()
				{
					public void onWayPointReach(MotionEvent me, int index) 
					{
						if (pathCrane.getNbWayPoints() == index + 1)
						{
							moveHook(des[1], sp);
							pathHook.addListener(new MotionPathListener()
							{
								public void onWayPointReach(MotionEvent me, int index) 
								{
									if (pathHook.getNbWayPoints() == index + 1)
									{
										moveCrane(des[2], sp);
										pathCrane.addListener(new MotionPathListener()
										{
											public void onWayPointReach(MotionEvent me, int index) 
											{
												if (pathCrane.getNbWayPoints() == index + 1)
												{
													moveHook(des[3], sp);
													pathHook.addListener(new MotionPathListener()
													{
														public void onWayPointReach(MotionEvent me, int index) 
														{
															if (pathHook.getNbWayPoints() == index + 1)
															{
																craneControl.stop();
																hookControl.stop();
															}
														}
													});
												}
											}
										});
									}
								}
							});
						}
					}
					
				});
				break;
			}
			case 3:
			{
				//Storage to AGV
//				moveCrane(des[0], sp);
//				moveHook(des[1], sp);
//				moveCrane(des[2], sp);
//				moveHook(des[3], sp);
			}
			case 4:
			{
				//AGV to vehicle
//				moveCrane(des[0], sp);
//				moveHook(des[1], sp);
//				moveSlider(des[2], sp);
//				moveHook(des[4], sp);
//				moveSlider(des[5], sp);
			}
		}
	}
	
	public void moveCrane(Vector3f des, float sp)
	{
		Vector3f startPosition, desPosition;
		startPosition = this.getLocalTranslation();
		desPosition = des;
		
		if(startPosition.distance(desPosition) > 0)
		{
			pathCrane.clearWayPoints();
			
			pathCrane.addWayPoint(startPosition);
			pathCrane.addWayPoint(desPosition);	
			
			craneControl.play();
		}
		System.out.println("Number of WayPoints: " + pathCrane.getNbWayPoints());
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
		System.out.println("Number of WayPoints: " + pathSlider.getNbWayPoints());
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
			pathHook.addWayPoint(startPosition);
			
			hookControl.play();
		}
		System.out.println("Number of WayPoints: " + pathHook.getNbWayPoints());
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
