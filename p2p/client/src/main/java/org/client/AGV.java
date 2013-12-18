package org.client;

import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
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

public class AGV extends Node implements MotionPathListener {

	boolean setWireFrame;

	private Spatial AGV;
//	private BitmapText wayPointsText;
//	private BitmapFont guiFont;
//	private Node nodeAGV;
//	private Node AllAgvNodes;
	Vector3f location;
	String Name;


	public AGV(String id, Vector3f location, Spatial AGV, String Name) 
	{
		super(id);
		
		this.Name = Name;
		this.AGV = AGV.clone();
		
		this.scale(2.5f);
		this.setName(name);
		
		this.attachChild(this.AGV);
	//	initMotionControl();

	}

//	public void addAGVcar(Vector3f location) {
//
//		AGV = assetManager.loadModel("Models/AGV/AGV.obj");
//		AGV.setLocalTranslation(location);
//		AGV.scale(2.5f);
//		Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
//		//mat.setColor("Color", ColorRGBA.Green);
//		AGV.setMaterial(mat);
//		AGV.setName(Name);
//		mat.getAdditionalRenderState().setWireframe(setWireFrame);
//		nodeAGV = new Node();
//		nodeAGV.attachChild(AGV); // make the AGV appear in the scene
//		AllAgvNodes.attachChild(nodeAGV);
//	}

//	public void initMotionControl() {
//		motionControl = new MotionEvent(AGV, path);
//		motionControl.setDirectionType(MotionEvent.Direction.Path);
//		motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
//		motionControl.setInitialDuration(0.5f);
//		motionControl.setSpeed(0.5f);
//		// guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
//		path.addListener(new MotionPathListener() {
//
//			public void onWayPointReach(MotionEvent control, int wayPointIndex) {
//
//				if (path.getNbWayPoints() == wayPointIndex + 1) {
//					motionControl2 = new MotionEvent(AGV, path2);
//					motionControl2.setDirectionType(MotionEvent.Direction.Path);
//					motionControl2.setRotation(new Quaternion()
//							.fromAngleNormalAxis(-FastMath.HALF_PI,
//									Vector3f.UNIT_Y));
//					motionControl2.setInitialDuration(0.5f);
//					motionControl2.setSpeed(0.5f);
//					motionControl2.play();
//
//					path2.addListener(new MotionPathListener() {
//						public void onWayPointReach(MotionEvent control,
//								int wayPointIndex) {
//							if (path2.getNbWayPoints() == wayPointIndex + 1) {
//								motionControl3 = new MotionEvent(AGV, path3);
//								motionControl3
//										.setDirectionType(MotionEvent.Direction.Path);
//								motionControl3.setRotation(new Quaternion()
//										.fromAngleNormalAxis(-FastMath.HALF_PI,
//												Vector3f.UNIT_Y));
//								motionControl3.setInitialDuration(0.5f);
//								motionControl3.setSpeed(0.5f);
//								motionControl3.play();
//
//								path3.addListener(new MotionPathListener() {
//
//									public void onWayPointReach(
//											MotionEvent control,
//											int wayPointIndex) {
//										if (path3.getNbWayPoints() == wayPointIndex + 1) {
//											motionControl4 = new MotionEvent(
//													AGV, path4);
//											motionControl4
//													.setDirectionType(MotionEvent.Direction.Path);
//											motionControl4
//													.setRotation(new Quaternion()
//															.fromAngleNormalAxis(
//																	-FastMath.HALF_PI,
//																	Vector3f.UNIT_Y));
//											motionControl4
//													.setInitialDuration(1f);
//											motionControl4.setSpeed(2f);
//											motionControl4.play();
//										}
//
//									}
//								});
//							}
//
//						}
//					});
//				}
//			}
//		});
//	}
	


	public void onWayPointReach(MotionEvent arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}
