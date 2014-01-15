package org.client;

import com.jme3.animation.LoopMode;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public abstract class Crane extends Node {
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
	protected Spatial container;
	protected Node hookNode = new Node();
	protected Node sliderNode = new Node();
	private String id;
<<<<<<< HEAD
	private boolean inProgress;
	
=======
	private boolean busy;

>>>>>>> b33be5d4798a315c1042a3eee8087ef10bded7cc
	private Vector3f position;

	public Crane(String id, Vector3f position, Spatial crane, Spatial slider,
			Spatial hook) {
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

		craneControl = new MotionEvent(this, pathCrane, SPEED,
				LoopMode.DontLoop);
		sliderControl = new MotionEvent(this.sliderNode, pathSlider, SPEED,
				LoopMode.DontLoop);
		hookControl = new MotionEvent(this.hookNode, pathHook, SPEED,
				LoopMode.DontLoop);
	}

	public void update(float tpf) {
		this.setLocalTranslation(this.getLocalTranslation());
	}

	public String getId() {
		return this.id;
	}
<<<<<<< HEAD
	
	public boolean isBusy()
	{
		return inProgress;
=======

	public boolean isBusy() {
		return busy;
>>>>>>> b33be5d4798a315c1042a3eee8087ef10bded7cc
	}

	public Vector3f getPosition() {
		return this.position;
	}

	public Spatial getContainer() {
		return container;
	}

	public void setContainer(Spatial cont) {
		this.container = cont.clone();
	}

	public void attachContainer() {
		this.container.scale(2);
		this.container.rotate(0, 90 * FastMath.DEG_TO_RAD, 0);
		this.container.setLocalTranslation(
				this.container.getLocalTranslation().x + 5,
				this.container.getLocalTranslation().y + 2.5f,
				this.container.getLocalTranslation().z + 0.5f);
		this.hookNode.attachChild(this.container);
	}

	public void deleteContainer() {
		this.hookNode.detachChild(this.container);
	}

	public float distance(String vehicleName, Vector3f location) {
		switch (vehicleName) {
		case "vrachtauto":
			distance = (location.x * 40 + 250) - getPosition().x;
			break;
		}

		return distance;
	}
<<<<<<< HEAD
	
	public void animation(int road, final Vector3f[] des, final float sp)
	{
		switch(road)
		{
			case 1:
			{
				//AGV to Storage
				this.inProgress = true;
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
=======

	public void animation(int road, final Vector3f[] des, final float sp) {
		switch (road) {
		case 1: {
			// AGV to Storage
			this.busy = true;
			moveCrane(des[3], sp);
			pathCrane.addListener(new MotionPathListener() {
				public void onWayPointReach(MotionEvent me, int index) {
					if (pathCrane.getNbWayPoints() == index + 1) {
						craneControl.stop();
						moveHook(des[2], sp);
						pathHook.addListener(new MotionPathListener() {
							public void onWayPointReach(MotionEvent me2,
									int index2) {
								if (pathHook.getNbWayPoints() == index2 + 1) {
									hookControl.stop();
									moveCrane(des[0], sp);
									pathCrane
											.addListener(new MotionPathListener() {
												public void onWayPointReach(
														MotionEvent me3,
														int index3) {
													if (pathCrane
															.getNbWayPoints() == index3 + 1) {
														craneControl.stop();
														hookControl.stop();
														moveSlider(des[1], sp);
														pathSlider
																.addListener(new MotionPathListener() {
																	public void onWayPointReach(
																			MotionEvent me4,
																			int index4) {
																		if (pathSlider
																				.getNbWayPoints() == index4 + 1) {
																			sliderControl
																					.stop();
																			moveHook(
																					des[5],
																					sp);
																			pathHook.addListener(new MotionPathListener() {
																				public void onWayPointReach(
																						MotionEvent me5,
																						int index5) {
																					if (pathHook
																							.getNbWayPoints() == index5 + 1) {
																						hookControl
																								.stop();
																						craneControl
																								.stop();
																						moveSlider(
																								des[4],
																								sp);
																						pathSlider
																								.addListener(new MotionPathListener() {

																									public void onWayPointReach(
																											MotionEvent me6,
																											int index6) {
																										if (pathSlider
																												.getNbWayPoints() == index6 + 1) {
																											sliderControl
																													.stop();
																											hookControl
																													.stop();
																											// System.out.println("CraneControl: "
																											// +
																											// craneControl.getPlayState());
																											// System.out.println("SliderControl: "
																											// +
																											// sliderControl.getPlayState());
																											// System.out.println("HookControl: "
																											// +
																											// hookControl.getPlayState());
																											// System.exit(1);
																										}
																									}

																								});
>>>>>>> b33be5d4798a315c1042a3eee8087ef10bded7cc
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
<<<<<<< HEAD
					
				});
				this.inProgress = false;
				break;
			}
			case 2:
			{
				//Storage to AVG
				this.inProgress = true;
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
=======
				}

			});
			this.busy = false;
			break;
		}
		case 2: {
			// Storage to AVG
			this.busy = true;
			moveCrane(des[0], sp);
			pathCrane.addListener(new MotionPathListener() {
				public void onWayPointReach(MotionEvent me, int index) {
					if (pathCrane.getNbWayPoints() == index + 1) {
						craneControl.stop();
						moveSlider(des[1], sp);
						pathSlider.addListener(new MotionPathListener() {
							public void onWayPointReach(MotionEvent me2,
									int index2) {
								if (pathSlider.getNbWayPoints() == index2 + 1) {
									sliderControl.stop();
									moveHook(des[5], sp);
									pathHook.addListener(new MotionPathListener() {
										public void onWayPointReach(
												MotionEvent me3, int index3) {
											if (pathHook.getNbWayPoints() == index3 + 1) {
												hookControl.stop();
												moveSlider(des[4], sp);
												pathSlider
														.addListener(new MotionPathListener() {
															public void onWayPointReach(
																	MotionEvent me4,
																	int index4) {
																if (pathSlider
																		.getNbWayPoints() == index4 + 1) {
																	sliderControl
																			.stop();
																	hookControl
																			.stop();
																	moveCrane(
																			des[3],
																			sp);
																	pathCrane
																			.addListener(new MotionPathListener() {
																				public void onWayPointReach(
																						MotionEvent me5,
																						int index5) {
																					if (pathCrane
																							.getNbWayPoints() == index5 + 1) {
																						craneControl
																								.stop();
																						sliderControl
																								.stop();
																						moveHook(
																								des[2],
																								sp);
																						pathHook.addListener(new MotionPathListener() {
																							public void onWayPointReach(
																									MotionEvent me6,
																									int index6) {
																								if (pathHook
																										.getNbWayPoints() == index6 + 1) {
																									hookControl
																											.stop();
																									sliderControl
																											.stop();
																									// System.out.println("CraneControl: "
																									// +
																									// craneControl.getPlayState());
																									// System.out.println("SliderControl: "
																									// +
																									// sliderControl.getPlayState());
																									// System.out.println("HookControl: "
																									// +
																									// hookControl.getPlayState());
																									// System.exit(1);
																								}
																							}
																						});
>>>>>>> b33be5d4798a315c1042a3eee8087ef10bded7cc
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
<<<<<<< HEAD
				});
				this.inProgress = false;
				break;
			}
			case 3:
			{
				//Truck to AGV
				this.inProgress = true;
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
=======
				}
			});
			this.busy = false;
			break;
		}
		case 3: {
			// Truck to AGV
			this.busy = true;
			moveCrane(des[2], sp);
			pathCrane.addListener(new MotionPathListener() {
				public void onWayPointReach(MotionEvent me, int index) {
					if (pathCrane.getNbWayPoints() == index + 1) {
						craneControl.stop();
						moveHook(des[1], sp);
						pathHook.addListener(new MotionPathListener() {
							public void onWayPointReach(MotionEvent me2,
									int index2) {
								if (pathHook.getNbWayPoints() == index2 + 1) {
									hookControl.stop();
									moveCrane(des[0], sp);
									pathCrane
											.addListener(new MotionPathListener() {
												public void onWayPointReach(
														MotionEvent me3,
														int index3) {
													if (pathCrane
															.getNbWayPoints() == index3 + 1) {
														craneControl.stop();
														hookControl.stop();
														moveHook(des[1], sp);
														pathHook.addListener(new MotionPathListener() {
															public void onWayPointReach(
																	MotionEvent me4,
																	int index4) {
																if (pathHook
																		.getNbWayPoints() == index4 + 1) {
																	hookControl
																			.stop();
																	craneControl
																			.stop();
																	// System.out.println("CraneControl: "
																	// +
																	// craneControl.getPlayState());
																	// System.out.println("SliderControl: "
																	// +
																	// sliderControl.getPlayState());
																	// System.out.println("HookControl: "
																	// +
																	// hookControl.getPlayState());
																	// System.exit(1);
																}
>>>>>>> b33be5d4798a315c1042a3eee8087ef10bded7cc
															}
														});
													}
												}
											});
								}
							}
						});
					}
<<<<<<< HEAD
				});
				this.inProgress = false;
				break;
			}
			case 4:
			{
				//AGV to Truck
				this.inProgress = true;
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
=======
				}
			});
			this.busy = false;
			break;
		}
		case 4: {
			// AGV to Truck
			this.busy = true;
			moveHook(des[1], sp);
			pathHook.addListener(new MotionPathListener() {
				public void onWayPointReach(MotionEvent me, int index) {
					if (pathHook.getNbWayPoints() == index + 1) {
						hookControl.stop();
						moveCrane(des[2], sp);
						pathCrane.addListener(new MotionPathListener() {
							public void onWayPointReach(MotionEvent me2,
									int index2) {
								if (pathCrane.getNbWayPoints() == index2 + 1) {
									craneControl.stop();
									moveHook(des[1], sp);
									pathHook.addListener(new MotionPathListener() {
										public void onWayPointReach(
												MotionEvent me3, int index3) {
											if (pathHook.getNbWayPoints() == index3 + 1) {
												hookControl.stop();
												craneControl.stop();
												moveCrane(des[0], sp);
												pathCrane
														.addListener(new MotionPathListener() {
															public void onWayPointReach(
																	MotionEvent me4,
																	int index4) {
																if (pathCrane
																		.getNbWayPoints() == index4 + 1) {
																	craneControl
																			.stop();
																	hookControl
																			.stop();
																	// System.out.println("CraneControl: "
																	// +
																	// craneControl.getPlayState());
																	// System.out.println("SliderControl: "
																	// +
																	// sliderControl.getPlayState());
																	// System.out.println("HookControl: "
																	// +
																	// hookControl.getPlayState());
																	// System.exit(1);
																}
>>>>>>> b33be5d4798a315c1042a3eee8087ef10bded7cc
															}
														});
											}
										}
									});
								}
							}
						});
					}
<<<<<<< HEAD
				});
				this.inProgress = false;
				break;
			}
=======
				}
			});
			this.busy = false;
			break;
		}
>>>>>>> b33be5d4798a315c1042a3eee8087ef10bded7cc
		}
	}

	public void moveCrane(Vector3f des, float sp) {
		Vector3f startPosition;
		startPosition = this.getLocalTranslation();

		pathCrane.clearWayPoints();
		pathCrane.addWayPoint(startPosition);

		if (startPosition.distance(des) > 0) {
			pathCrane.addWayPoint(des);
		}

		// craneControl.setSpeed(sp);
		craneControl.play();
	}

	public void moveSlider(Vector3f des, float sp) {
		Vector3f startPosition;
		startPosition = this.sliderNode.getLocalTranslation();

		pathSlider.clearWayPoints();
		pathSlider.addWayPoint(startPosition);

		if (startPosition.distance(des) > 0) {
			pathSlider.addWayPoint(des);
		}

		// sliderControl.setSpeed(sp);
		sliderControl.play();
	}

	public void moveHook(Vector3f des, float sp) {
		Vector3f startPosition;
		startPosition = this.hookNode.getLocalTranslation();
		pathHook.clearWayPoints();

		if (startPosition.distance(des) > 0) {
			pathHook.addWayPoint(startPosition);
			pathHook.addWayPoint(des);
			pathHook.addWayPoint(startPosition);
			// hookControl.setSpeed(sp);
			hookControl.play();
		}
	}
}
