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
	public MotionPath path, path2, path3, path4;
	public MotionEvent motionControl, motionControl2, motionControl3,
			motionControl4;
	private Spatial AGV, AGV2;
	public BitmapText wayPointsText;
	private AssetManager assetManager;
	private BitmapFont guiFont;
	public Node nodeAGV;
	private Node AllAgvNodes;
	Vector3f location;
	String Name;

	public AGV(Vector3f location, AssetManager am, Node allAgvNodes,
			boolean setWireFrame, String Name) {
		this.AllAgvNodes = allAgvNodes;
		this.assetManager = am;
		this.setWireFrame = setWireFrame;
		this.Name = Name;
		MotionPath1();
		addAGVcar(location);
		initMotionControl();
		MotionPath2();
		MotionPath3();
		MotionPath4();
	}

	public void addAGVcar(Vector3f location) {

		AGV = assetManager.loadModel("Models/AGV/AGV.obj");
		AGV.setLocalTranslation(location);
		AGV.scale(10);
		Material mat = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		mat.setColor("Color", ColorRGBA.Green);
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
		motionControl.setRotation(new Quaternion().fromAngleNormalAxis(
				-FastMath.HALF_PI, Vector3f.UNIT_Y));
		motionControl.setInitialDuration(5f);
		motionControl.setSpeed(2f);
		// guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		path.addListener(new MotionPathListener() {

			public void onWayPointReach(MotionEvent control, int wayPointIndex) {

				if (path.getNbWayPoints() == wayPointIndex + 1) {
					motionControl2 = new MotionEvent(AGV, path2);
					motionControl2.setDirectionType(MotionEvent.Direction.Path);
					motionControl2.setRotation(new Quaternion()
							.fromAngleNormalAxis(-FastMath.HALF_PI,
									Vector3f.UNIT_Y));
					motionControl2.setInitialDuration(5f);
					motionControl2.setSpeed(2f);
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
								motionControl3.setInitialDuration(10f);
								motionControl3.setSpeed(2f);
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
													.setInitialDuration(10f);
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

//	public void MotionPath1() {
//		path = new MotionPath();
//		path.addWayPoint(new Vector3f(26, 260, 261));
//		path.addWayPoint(new Vector3f(-117, 260, 261));
//		path.addWayPoint(new Vector3f(-117, 260, 336));
//		path.setCurveTension(0.3f);
//		path.enableDebugShape(assetManager, AllAgvNodes);
//
//	}
//
//	public void MotionPath2() {
//		path2 = new MotionPath();
//		path2.addWayPoint(new Vector3f(-118, 260, 338));
//		path2.addWayPoint(new Vector3f(212, 260, 336));
//		path2.addWayPoint(new Vector3f(212, 260, 407));
//		path2.setCurveTension(0.3f);
//		path2.enableDebugShape(assetManager, AllAgvNodes);
//
//	}
//
//	public void MotionPath3() {
//		path3 = new MotionPath();
//		path3.addWayPoint(new Vector3f(211, 260, 409));
//		path3.addWayPoint(new Vector3f(435, 260, 336));
//		path3.addWayPoint(new Vector3f(435, 260, 404));
//		path3.addWayPoint(new Vector3f(1480, 260, 336));
//		path3.addWayPoint(new Vector3f(1480, 260, -310));
//		path3.addWayPoint(new Vector3f(522, 260, -310));
//		path3.setCurveTension(0.3f);
//		path3.enableDebugShape(assetManager, AllAgvNodes);
//
//	}

//	public void MotionPath4() {
//	path4 = new MotionPath();
//	path4.addWayPoint(new Vector3f(523, 260, -312));
//	path4.addWayPoint(new Vector3f(240, 260f, 50));
//	path4.addWayPoint(new Vector3f(260, 260f, 80));
//	path4.addWayPoint(new Vector3f(500, 260f, 250));
//	path4.setCurveTension(0.3f);
//	path4.enableDebugShape(assetManager, AllAgvNodes);
//
//}
	
	public void MotionPath1() {
		path = new MotionPath();
		path.addWayPoint(new Vector3f(1087.0f, 260.0f, 119.0f));
		path.addWayPoint(new Vector3f(-510.0f, 260.0f, 119.0f));
		path.addWayPoint(new Vector3f(-510.0f, 260.0f, 765.0f));
		path.addWayPoint(new Vector3f(1087.0f, 260.0f, 765.0f));
		path.setCurveTension(0.3f);
		path.enableDebugShape(assetManager, AllAgvNodes);

	}

	public void MotionPath2() {
		path2 = new MotionPath();
		path2.addWayPoint(new Vector3f(1082.33f, 260.0f, 126.66f));
		path2.addWayPoint(new Vector3f(-500.0f, 260.0f, 126.66f));
		path2.addWayPoint(new Vector3f(-500.0f, 260.0f, 756.66f));
		path2.addWayPoint(new Vector3f(1082.33f, 260.0f, 756.66f));
		path2.setCurveTension(0.3f);
		path2.enableDebugShape(assetManager, AllAgvNodes);

	}

	public void MotionPath3() {
		path3 = new MotionPath();
		path3.addWayPoint(new Vector3f(1073.66f, 260.0f, 134.33f));
		path3.addWayPoint(new Vector3f(-490.0f, 260.0f, 134.33f));
		path3.addWayPoint(new Vector3f(-490.0f, 260.0f, 748.33f));
		path3.addWayPoint(new Vector3f(1073.66f, 260.0f, 748.33f));
		path3.setCurveTension(0.3f);
		path3.enableDebugShape(assetManager, AllAgvNodes);

	}
	public void MotionPath4() {
		path4 = new MotionPath();
		path4.addWayPoint(new Vector3f(1067.0f, 260.0f, 142.0f));
		path4.addWayPoint(new Vector3f(-480.0f, 260.0f, 142.0f));
		path4.addWayPoint(new Vector3f(-480.0f, 260.0f, 740.0f));
		path4.addWayPoint(new Vector3f(1067.0f, 260.0f, 740.0f));
		path4.setCurveTension(0.3f);
		path4.enableDebugShape(assetManager, AllAgvNodes);

	}

}