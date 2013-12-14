package org.client;

import com.jme3.animation.LoopMode;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Spline;
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
	private float [] distance = new float []{};
	
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
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public Vector3f getPosition()
	{
		return this.position;
	}
	
	public float [] distance(Vector3f location)
	{
		int id = 0;
		
		distance[id] = location.distance(getPosition());
		
		id++;
		return distance;
	}
	
	public void animation(int action, final Vector3f[] des, final float sp)
	{
		switch(action)
		{
			case 1:
			{
				//Vehicle to AGV
				moveCrane(des[0], sp);
				pathCrane.addListener(new MotionPathListener()
				{
					public void onWayPointReach(MotionEvent me, int index) 
					{
						if (pathCrane.getNbWayPoints() == index + 1)
						{
							moveSlider(des[1], sp);
							pathSlider.addListener(new MotionPathListener()
							{
								public void onWayPointReach(MotionEvent me, int index) 
								{
									if (pathSlider.getNbWayPoints() == index + 1)
									{
										moveHook(des[2], sp);
										pathHook.addListener(new MotionPathListener()
										{
											public void onWayPointReach(MotionEvent me, int index) 
											{
												if (pathHook.getNbWayPoints() == index + 1)
												{
													moveSlider(des[3], sp);
													pathSlider.addListener(new MotionPathListener()
													{
														public void onWayPointReach(MotionEvent me, int index) 
														{
															if (pathSlider.getNbWayPoints() == index + 1)
															{
																moveHook(des[4], sp);
																pathHook.addListener(new MotionPathListener()
																{
																	public void onWayPointReach(MotionEvent me, int index) 
																	{
																		if (pathHook.getNbWayPoints() == index + 1)
																		{			
																			craneControl.stop();
																			sliderControl.stop();
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
						}
					}
					
				});
				break;
			}
			case 2:
			{
				//AGV to vehicle
				moveCrane(des[0], sp);
				System.out.println("moveCrane");
				pathCrane.addListener(new MotionPathListener()
				{
					public void onWayPointReach(MotionEvent me, int index) 
					{
						if (pathCrane.getNbWayPoints() == index + 1)
						{
							moveHook(des[4], sp);
							System.out.println("moveHook");
							pathHook.addListener(new MotionPathListener()
							{
								public void onWayPointReach(MotionEvent me, int index) 
								{
									if (pathHook.getNbWayPoints() == index + 1)
									{
										moveSlider(des[1], sp);
										
										pathSlider.addListener(new MotionPathListener()
										{
											public void onWayPointReach(MotionEvent me, int index) 
											{
												if (pathSlider.getNbWayPoints() == index + 1)
												{
													moveHook(des[2], sp);
													pathHook.addListener(new MotionPathListener()
													{
														public void onWayPointReach(MotionEvent me, int index) 
														{
															if (pathHook.getNbWayPoints() == index + 1)
															{
																moveSlider(des[3], sp);
																pathSlider.addListener(new MotionPathListener()
																{
																	public void onWayPointReach(MotionEvent me, int index) 
																	{
																		if (pathSlider.getNbWayPoints() == index + 1)
																		{			
																			craneControl.stop();
																			sliderControl.stop();
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
						}
					}
					
				});
				break;
			}
			case 3:
			{
				//AGV to Storage
				moveCrane(des[5], sp);
				pathCrane.addListener(new MotionPathListener()
				{
					public void onWayPointReach(MotionEvent me, int index) 
					{
						if (pathCrane.getNbWayPoints() == index + 1)
						{
							moveHook(des[2], sp);
							pathHook.addListener(new MotionPathListener()
							{
								public void onWayPointReach(MotionEvent me, int index) 
								{
									if (pathHook.getNbWayPoints() == index + 1)
									{
										moveCrane(des[0], sp);
										pathCrane.addListener(new MotionPathListener()
										{
											public void onWayPointReach(MotionEvent me, int index) 
											{
												if (pathCrane.getNbWayPoints() == index + 1)
												{
													moveSlider(des[1], sp);
													pathSlider.addListener(new MotionPathListener()
													{
														public void onWayPointReach(MotionEvent me, int index) 
														{
															if (pathSlider.getNbWayPoints() == index + 1)
															{
																moveHook(des[4], sp);
																pathHook.addListener(new MotionPathListener()
																{
																	public void onWayPointReach(MotionEvent me, int index) 
																	{
																		if (pathHook.getNbWayPoints() == index + 1)
																		{
																			moveSlider(des[3], sp);
																			pathSlider.addListener(new MotionPathListener()
																			{
																				public void onWayPointReach(MotionEvent me, int index) 
																				{
																					if (pathSlider.getNbWayPoints() == index + 1)
																					{
																						craneControl.stop();
																						hookControl.stop();
																						sliderControl.stop();
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
									}
								}
							});
						}
					}
				});
				break;
			}
			case 4:
			{
				//Storage to AVG
				moveCrane(des[0], sp);
				pathCrane.addListener(new MotionPathListener()
				{
					public void onWayPointReach(MotionEvent me, int index) 
					{
						if (pathCrane.getNbWayPoints() == index + 1)
						{
							moveSlider(des[1], sp);
							pathSlider.addListener(new MotionPathListener()
							{
								public void onWayPointReach(MotionEvent me, int index) 
								{
									if (pathSlider.getNbWayPoints() == index + 1)
									{
										moveHook(des[4], sp);
										pathHook.addListener(new MotionPathListener()
										{
											public void onWayPointReach(MotionEvent me, int index) 
											{
												if(pathHook.getNbWayPoints() == index + 1)
												{
													moveSlider(des[3], sp);	
													pathSlider.addListener(new MotionPathListener()
													{
														public void onWayPointReach(MotionEvent me, int index) 
														{
															if (pathSlider.getNbWayPoints() == index + 1)
															{
																moveCrane(des[5], sp);
																pathCrane.addListener(new MotionPathListener()
																{
																	public void onWayPointReach(MotionEvent me, int index) 
																	{
																		if (pathCrane.getNbWayPoints() == index + 1)
																		{
																			moveHook(des[2], sp);
																			pathHook.addListener(new MotionPathListener()
																			{
																				public void onWayPointReach(MotionEvent me, int index) 
																				{
																					if (pathHook.getNbWayPoints() == index + 1)
																					{
																						craneControl.stop();
																						sliderControl.stop();
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
									}
								}
							});
						}
					}
				});
				break;
			}
		}
	}
	
	public void moveCrane(Vector3f des, float sp)
	{
		Vector3f startPosition, desPosition;
		startPosition = this.getLocalTranslation();
		desPosition = des;
		
		pathCrane.clearWayPoints();
		pathCrane.addWayPoint(startPosition);
		
		if(startPosition.distance(desPosition) > 0)
		{			
			pathCrane.addWayPoint(desPosition);	
		}
		else
		{
			Vector3f desPos2 = new Vector3f(des.x+0.001f, des.y, des.z+0.001f);
			pathCrane.addWayPoint(desPos2);
		}
		
		craneControl.play();
		System.out.println("Number of WayPoints: " + pathCrane.getNbWayPoints());
	}
	
	public void moveSlider(Vector3f des, float sp)
	{
		Vector3f startPosition, desPosition;
		startPosition = sliderNode.getLocalTranslation();
		desPosition = des;
		
		pathSlider.clearWayPoints();
		pathSlider.addWayPoint(startPosition);
		
		if(startPosition.distance(desPosition) > 0)
		{			
			pathSlider.addWayPoint(desPosition);	
		}
		else
		{
			Vector3f desPos2 = new Vector3f(des.x+0.001f, des.y, des.z+0.001f);
			pathSlider.addWayPoint(desPos2);
		}
		
		sliderControl.play();
		System.out.println("Number of WayPoints: " + pathSlider.getNbWayPoints());
	}
	
	public void moveHook(Vector3f des, float sp)
	{
		Vector3f startPosition, desPosition;
		startPosition = hookNode.getLocalTranslation();
		desPosition = des;
		pathHook.clearWayPoints();
		
		if(startPosition.distance(desPosition) > 0)
		{	
			pathHook.addWayPoint(startPosition);
			pathHook.addWayPoint(desPosition);	
			pathHook.addWayPoint(startPosition);
			hookControl.play();
		}
		System.out.println("Number of WayPoints: " + pathHook.getNbWayPoints());
	}

	public void onWayPointReach(MotionEvent control, int wayPointIndex)
	{
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
