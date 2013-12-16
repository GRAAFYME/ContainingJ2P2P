package org.client;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/*
 * Authors
 * Joshua Bergsma
 * Remco de Bruin
 * Yme van der Graaf
 * Jeffrey Harders
 * Arjen Pander
 * Melinda de Roo 
 * */

public class AGV {

	boolean setWireFrame;
	public MotionPath path, path2, path3, path4, StartingPoint, CrossStartPoint, toSeaCrane, toTrucks, TrucksLoaded;
	public MotionEvent motionControl, motionControl2, motionControl3, motionControl4;
	private Spatial AGV;
	public BitmapText wayPointsText;
	private AssetManager assetManager;
	private BitmapFont guiFont;
	public Node nodeAGV;
	private Node AllAgvNodes;
	Vector3f location;
	String Name;
	float x1 = -470f;
	float z1 = 150f;
	float x2 = -470f;
	float x3 = -445f;
	float x4 = -470f;
	float x5 = -470f;
	float x6 = -470f;
	float z2 = 117;

	public AGV(Vector3f location, AssetManager am, Node allAgvNodes,
			boolean setWireFrame, String Name) {
		this.AllAgvNodes = allAgvNodes;
		this.assetManager = am;
		this.setWireFrame = setWireFrame;
		this.Name = Name;
		MotionPath1();
		addAGVcar(location);
		initMotionControl();
	//	MotionPath2();
	//	MotionPath3();
	//	MotionPath4();
		StartPoint();
		CrossingStart();
		CrossingToSeaCrane();
		CrossingToTrucks();
		CrossingTrucksLoaded();
	}

	public void addAGVcar(Vector3f location) {

		AGV = assetManager.loadModel("Models/AGV/AGV.obj");
		AGV.setLocalTranslation(location);
		AGV.scale(2.5f);
		Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
		//mat.setColor("Color", ColorRGBA.Green);
		AGV.setMaterial(mat);
		AGV.setName(Name);
		mat.getAdditionalRenderState().setWireframe(setWireFrame);
		nodeAGV = new Node();
		nodeAGV.attachChild(AGV); // make the AGV appear in the scene
		AllAgvNodes.attachChild(nodeAGV);
	}

	public void initMotionControl() {
		motionControl = new MotionEvent(AGV, path);
		motionControl.setDirectionType(MotionEvent.Direction.Path);
		motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
		motionControl.setInitialDuration(0.5f);
		motionControl.setSpeed(0.5f);
		// guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		path.addListener(new MotionPathListener() {

			public void onWayPointReach(MotionEvent control, int wayPointIndex) {

				if (path.getNbWayPoints() == wayPointIndex + 1) {
					motionControl2 = new MotionEvent(AGV, path2);
					motionControl2.setDirectionType(MotionEvent.Direction.Path);
					motionControl2.setRotation(new Quaternion()
							.fromAngleNormalAxis(-FastMath.HALF_PI,
									Vector3f.UNIT_Y));
					motionControl2.setInitialDuration(0.5f);
					motionControl2.setSpeed(0.5f);
					motionControl2.play();

					path2.addListener(new MotionPathListener() {
						public void onWayPointReach(MotionEvent control,
								int wayPointIndex) {
							if (path2.getNbWayPoints() == wayPointIndex + 1) {
								motionControl3 = new MotionEvent(AGV, path3);
								motionControl3
										.setDirectionType(MotionEvent.Direction.Path);
								motionControl3.setRotation(new Quaternion()
										.fromAngleNormalAxis(-FastMath.HALF_PI,
												Vector3f.UNIT_Y));
								motionControl3.setInitialDuration(0.5f);
								motionControl3.setSpeed(0.5f);
								motionControl3.play();

								path3.addListener(new MotionPathListener() {

									public void onWayPointReach(
											MotionEvent control,
											int wayPointIndex) {
										if (path3.getNbWayPoints() == wayPointIndex + 1) {
											motionControl4 = new MotionEvent(
													AGV, path4);
											motionControl4
													.setDirectionType(MotionEvent.Direction.Path);
											motionControl4
													.setRotation(new Quaternion()
															.fromAngleNormalAxis(
																	-FastMath.HALF_PI,
																	Vector3f.UNIT_Y));
											motionControl4
													.setInitialDuration(1f);
											motionControl4.setSpeed(2f);
											motionControl4.play();
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
	
	public void WayUpFull(){
	
	}
	public void WayUpEmpty(){
		
	}
	public void WayDownFull(){
		
	}
	public void WayDownEmpty(){
		
	}
	public void StartPoint(){

		for (int i = 0; i<50; i++)
		{
		StartingPoint = new MotionPath();
		StartingPoint = new MotionPath();
		StartingPoint.addWayPoint(new Vector3f(x1, 260f, z1));
		StartingPoint.addWayPoint(new Vector3f(x1, 260f, z2));
		StartingPoint.enableDebugShape(assetManager, AllAgvNodes);
		x1 += 25;
		}
		
		for (int j = 0; j<50; j++)
		{
			StartingPoint = new MotionPath();
			StartingPoint.addWayPoint(new Vector3f(x5, 260f, 735f));
			StartingPoint.addWayPoint(new Vector3f(x5, 260f, 767f));
			StartingPoint.enableDebugShape(assetManager, AllAgvNodes);
			x5+= 25;
		}
	}
	public void CrossingStart(){
		
		for (int i = 0; i<50; i++)
		{
		CrossStartPoint = new MotionPath();
		CrossStartPoint.addWayPoint(new Vector3f(x4, 260f, z2));
		CrossStartPoint.addWayPoint(new Vector3f(x3, 260f, z2));
		CrossStartPoint.enableDebugShape(assetManager, AllAgvNodes);
		x4 += 25;
		}
		
		for (int j = 0; j<50; j++)
		{
		CrossStartPoint = new MotionPath();
		CrossStartPoint.addWayPoint(new Vector3f(x6, 260f, 767f));
		CrossStartPoint.addWayPoint(new Vector3f(x3, 260f, 767f));
		CrossStartPoint.enableDebugShape(assetManager, AllAgvNodes);
		x6 += 25;
		}
		
	}
	public void CrossingToSeaCrane(){
		
		toSeaCrane = new MotionPath();
		toSeaCrane.addWayPoint(new Vector3f (-470, 260f, 117));
		toSeaCrane.addWayPoint(new Vector3f (-685, 260f, 117));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260f, 767f));
		toSeaCrane.setCurveTension(0.0f);
		toSeaCrane.enableDebugShape(assetManager, AllAgvNodes);
		
	}
	public void CrossingToTrucks(){
		toTrucks = new MotionPath();
		toTrucks.addWayPoint(new Vector3f (-470, 260f, 117));
		toTrucks.addWayPoint(new Vector3f (-470, 260f, 80));
		toTrucks.addWayPoint(new Vector3f (230, 260f, 80));
		toTrucks.setCurveTension(0.0f);
		toTrucks.enableDebugShape(assetManager, AllAgvNodes);
	}
	public void CrossingTrucksLoaded(){
		TrucksLoaded = new MotionPath();
		TrucksLoaded.addWayPoint(new Vector3f (230, 260f, 80));
		TrucksLoaded.addWayPoint(new Vector3f (230, 260f, 90));
		TrucksLoaded.addWayPoint(new Vector3f (-470, 260f, 90));
		TrucksLoaded.setCurveTension(0.0f);
		TrucksLoaded.enableDebugShape(assetManager, AllAgvNodes);
	}


	public void MotionPath1() {
		path = new MotionPath();
		path.addWayPoint(new Vector3f(778.0f, 260.0f, 152.0f));
		path.addWayPoint(new Vector3f(778.0f, 260.0f, 131.0f));

		path.setCurveTension(0.0f);
		path.enableDebugShape(assetManager, AllAgvNodes);

	}
	public void MotionPath2() {
		path2 = new MotionPath();
		path2.addWayPoint(new Vector3f(778.0f, 260.0f, 131.0f));
		path2.addWayPoint(new Vector3f(-685, 260.0f, 131f));
		path2.setCurveTension(0.f);
		path2.enableDebugShape(assetManager, AllAgvNodes);

	}

	public void MotionPath3() {
		path3 = new MotionPath();
		path3.addWayPoint(new Vector3f(-685, 260.0f, 131f));
		path3.addWayPoint(new Vector3f(-685.0f, 260.0f, 700));

		path3.setCurveTension(0.0f);
		path3.enableDebugShape(assetManager, AllAgvNodes);

	}

	public void MotionPath4() {
		path4 = new MotionPath();
		path4.addWayPoint(new Vector3f(1067.0f, 260.0f, 142.0f));
		path4.addWayPoint(new Vector3f(-480.0f, 260.0f, 142.0f));
		path4.addWayPoint(new Vector3f(-480.0f, 260.0f, 740.0f));
		path4.addWayPoint(new Vector3f(1067.0f, 260.0f, 740.0f));
		path4.setCurveTension(0.0f);
		path4.enableDebugShape(assetManager, AllAgvNodes);

	}
}
