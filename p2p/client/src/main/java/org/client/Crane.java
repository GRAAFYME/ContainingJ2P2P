package org.client;

import com.jme3.animation.LoopMode;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public abstract class Crane extends Node
{
	private final float SPEED = 0.5f;
	private MotionPath pathCrane = new MotionPath();
	private MotionPath pathSlider = new MotionPath();
	private MotionPath pathHook = new MotionPath();
	private MotionEvent craneControl;
	private MotionEvent sliderControl;
	private MotionEvent hookControl;
	private float distance;
	protected Spatial crane;
	protected Spatial slider;
	protected Spatial hook;
	protected Node hookNode = new Node();
	protected Node sliderNode = new Node();
	private String id;
	private boolean busy;
	
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
	
	public boolean isBusy()
	{
		return busy;
	}
	
	public Vector3f getPosition()
	{
		return this.position;
	}
	
	public float distance(String vehicleName, Vector3f location)
	{
		switch(vehicleName)
		{
			case "vrachtauto":
				float exactLoc = (location.x-1)*40 + 250;
				System.out.println(exactLoc);
				System.out.println(getPosition().x);
				distance = (location.x*40+250) - getPosition().x;
				break;
		}

		return distance;
	}
	
	public void animation(int road, final Vector3f[] des, final float sp)
	{
		switch(road)
		{
			case 1:
			{
				//AGV to Storage
				this.busy = true;
				moveCrane(des[3], sp);
				pathCrane.addListener(new MotionPathListener()
				{
					public void onWayPointReach(MotionEvent me, int index) 
					{
						if(pathCrane.getNbWayPoints() == index + 1)
						{
							craneControl.stop();
							moveHook(des[2], sp);
							pathHook.addListener(new MotionPathListener()
							{
								public void onWayPointReach(MotionEvent me2,int index2) 
								{
									if(pathHook.getNbWayPoints() == index2 + 1)
									{
										hookControl.stop();
										moveCrane(des[0], sp);
										pathCrane.addListener(new MotionPathListener()
										{
											public void onWayPointReach(MotionEvent me3, int index3)
											{
												if(pathCrane.getNbWayPoints() == index3 + 1)
												{
													craneControl.stop();
													hookControl.stop();
													moveSlider(des[1], sp);
													pathSlider.addListener(new MotionPathListener()
													{
														public void onWayPointReach(MotionEvent me4, int index4)
														{
															if(pathSlider.getNbWayPoints() == index4 + 1)
															{
																sliderControl.stop();
																moveHook(des[5], sp);
																pathHook.addListener(new MotionPathListener()
																{
																	public void onWayPointReach(MotionEvent me5, int index5) 
																	{
																		if(pathHook.getNbWayPoints() == index5 + 1)
																		{
																			hookControl.stop();
																			craneControl.stop();
																			moveSlider(des[4], sp);
																			pathSlider.addListener(new MotionPathListener()
																			{

																				public void onWayPointReach(MotionEvent me6, int index6)
																				{
																					if(pathSlider.getNbWayPoints() == index6 + 1)
																					{
																						sliderControl.stop();
																						hookControl.stop();
																						//System.out.println("CraneControl: " + craneControl.getPlayState());
																						//System.out.println("SliderControl: " + sliderControl.getPlayState());
																						//System.out.println("HookControl: " + hookControl.getPlayState());
																						//System.exit(1);
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
				this.busy = false;
				break;
			}
			case 2:
			{
				//Storage to AVG
				this.busy = true;
				moveCrane(des[0], sp);
				pathCrane.addListener(new MotionPathListener()
				{
					public void onWayPointReach(MotionEvent me, int index) 
					{
						if(pathCrane.getNbWayPoints() == index + 1)
						{
							craneControl.stop();
							moveSlider(des[1], sp);
							pathSlider.addListener(new MotionPathListener()
							{
								public void onWayPointReach(MotionEvent me2, int index2)
								{
									if(pathSlider.getNbWayPoints() == index2 + 1)
									{
										sliderControl.stop();
										moveHook(des[5], sp);
										pathHook.addListener(new MotionPathListener()
										{
											public void onWayPointReach(MotionEvent me3, int index3)
											{
												if(pathHook.getNbWayPoints() == index3 + 1)
												{
													hookControl.stop();
													moveSlider(des[4], sp);
													pathSlider.addListener(new MotionPathListener()
													{
														public void onWayPointReach(MotionEvent me4, int index4)
														{
															if(pathSlider.getNbWayPoints() == index4 + 1)
															{
																sliderControl.stop();
																hookControl.stop();
																moveCrane(des[3], sp);
																pathCrane.addListener(new MotionPathListener()
																{
																	public void onWayPointReach(MotionEvent me5, int index5)
																	{
																		if(pathCrane.getNbWayPoints() == index5 + 1)
																		{
																			craneControl.stop();
																			sliderControl.stop();
																			moveHook(des[2], sp);
																			pathHook.addListener(new MotionPathListener()
																			{
																				public void onWayPointReach(MotionEvent me6, int index6)
																				{
																					if(pathHook.getNbWayPoints() == index6 + 1)
																					{
																						hookControl.stop();
																						sliderControl.stop();
																						//System.out.println("CraneControl: " + craneControl.getPlayState());
																						//System.out.println("SliderControl: " + sliderControl.getPlayState());
																						//System.out.println("HookControl: " + hookControl.getPlayState());
																						//System.exit(1);
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
				this.busy = false;
				break;
			}
			case 3:
			{
				//Truck to AGV
				this.busy = true;
				moveCrane(des[2], sp);
				pathCrane.addListener(new MotionPathListener()
				{
					public void onWayPointReach(MotionEvent me, int index) 
					{
						if(pathCrane.getNbWayPoints() == index + 1)
						{
							craneControl.stop();
							moveHook(des[1], sp);
							pathHook.addListener(new MotionPathListener()
							{
								public void onWayPointReach(MotionEvent me2, int index2)
								{
									if(pathHook.getNbWayPoints() == index2 + 1)
									{
										hookControl.stop();
										moveCrane(des[0], sp);
										pathCrane.addListener(new MotionPathListener()
										{
											public void onWayPointReach(MotionEvent me3, int index3)
											{
												if(pathCrane.getNbWayPoints() == index3 + 1)
												{
													craneControl.stop();
													hookControl.stop();
													moveHook(des[1], sp);
													pathHook.addListener(new MotionPathListener()
													{
														public void onWayPointReach(MotionEvent me4,int index4)
														{
															if(pathHook.getNbWayPoints() == index4 + 1)
															{
																hookControl.stop();
																craneControl.stop();
																//System.out.println("CraneControl: " + craneControl.getPlayState());
																//System.out.println("SliderControl: " + sliderControl.getPlayState());
																//System.out.println("HookControl: " + hookControl.getPlayState());
																//System.exit(1);
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
				this.busy = false;
				break;
			}
			case 4:
			{
				//AGV to Truck
				this.busy = true;
				moveHook(des[1], sp);
				pathHook.addListener(new MotionPathListener()
				{
					public void onWayPointReach(MotionEvent me, int index) 
					{
						if(pathHook.getNbWayPoints() == index + 1)
						{
							hookControl.stop();
							moveCrane(des[2], sp);
							pathCrane.addListener(new MotionPathListener()
							{
								public void onWayPointReach(MotionEvent me2, int index2)
								{
									if(pathCrane.getNbWayPoints() == index2 + 1)
									{
										craneControl.stop();
										moveHook(des[1], sp);
										pathHook.addListener(new MotionPathListener()
										{
											public void onWayPointReach(MotionEvent me3, int index3)
											{
												if(pathHook.getNbWayPoints() == index3 + 1)
												{
													hookControl.stop();
													craneControl.stop();
													moveCrane(des[0], sp);
													pathCrane.addListener(new MotionPathListener()
													{
														public void onWayPointReach(MotionEvent me4,int index4)
														{
															if(pathCrane.getNbWayPoints() == index4 + 1)
															{
																craneControl.stop();
																hookControl.stop();
																//System.out.println("CraneControl: " + craneControl.getPlayState());
																//System.out.println("SliderControl: " + sliderControl.getPlayState());
																//System.out.println("HookControl: " + hookControl.getPlayState());
																//System.exit(1);
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
				this.busy = false;
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
		
		//craneControl.setSpeed(sp);
		craneControl.play();
		System.out.println("Number of WayPoints: " + pathCrane.getNbWayPoints());
	}
	
	public void moveSlider(Vector3f des, float sp)
	{
		Vector3f startPosition, desPosition;
		startPosition = this.sliderNode.getLocalTranslation();
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
		
		//sliderControl.setSpeed(sp);
		sliderControl.play();
		System.out.println("Number of WayPoints: " + pathSlider.getNbWayPoints());
	}
	
	public void moveHook(Vector3f des, float sp)
	{
		Vector3f startPosition, desPosition;
		startPosition = this.hookNode.getLocalTranslation();
		desPosition = des;
		pathHook.clearWayPoints();
		
		if(startPosition.distance(desPosition) > 0)
		{	
			pathHook.addWayPoint(startPosition);
			pathHook.addWayPoint(desPosition);	
			pathHook.addWayPoint(startPosition);
			//hookControl.setSpeed(sp);
			hookControl.play();
		}
		System.out.println("Number of WayPoints: " + pathHook.getNbWayPoints());
	}
}
