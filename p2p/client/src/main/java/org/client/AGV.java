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

public class AGV{
	
	private boolean setWireFrame;
    private MotionPath path;
    public MotionEvent motionControl, motionControl2;
	private Spatial AutomaticGuidedVehicle, AGV2;
	public BitmapText wayPointsText;
	public Spatial AGV;
	private AssetManager assetManager;
	private BitmapFont guiFont;
	public Node nodeAGV;
	private Node AllAgvNodes;
	
	public AGV(Vector3f location, AssetManager am, Node allAgvNodes){
		AllAgvNodes = allAgvNodes;
		assetManager = am;
		tempWaypoint();
		addAGVcar(location);
		initMotionControl();
	}
	
    public AGV(AssetManager assetManager, boolean setWireFrame){
    	this.assetManager = assetManager;
    	this.setWireFrame = setWireFrame;
    	CreatePath();  
    }
    
    public void CreatePath(){
    path = new MotionPath();
    path.addWayPoint(new Vector3f(60, 117, 130));
    path.addWayPoint(new Vector3f(60, 117, 240));
    path.addWayPoint(new Vector3f(124, 117, 240));
    path.addWayPoint(new Vector3f(124, 117, -141));
    path.addWayPoint(new Vector3f(-97, 117, -141));
    path.addWayPoint(new Vector3f(-97, 117, 11));
    path.addWayPoint(new Vector3f(-141, 117, 11));
    path.setCurveTension(0.3f);
    path.enableDebugShape(assetManager, null);
    

    
    motionControl = new MotionEvent(AutomaticGuidedVehicle, path);
    motionControl2 = new MotionEvent(AGV2, path);
    
    motionControl.setDirectionType(MotionEvent.Direction.Path);
    motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
    motionControl.setInitialDuration(10f);
    motionControl.setSpeed(0.5f);  
    
    
    motionControl2.setDirectionType(MotionEvent.Direction.Path);
    motionControl2.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
    motionControl2.setInitialDuration(10f);
    motionControl2.setSpeed(0.5f); 
    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
   final BitmapText wayPointsText = new BitmapText(guiFont, false);
    wayPointsText.setSize(guiFont.getCharSet().getRenderedSize());

    //guiFont.attachChild(wayPointsText);

    path.addListener(new MotionPathListener() {

        public void onWayPointReach(MotionEvent control, int wayPointIndex) {
//            if (path.getNbWayPoints() == wayPointIndex + 1) {
//                wayPointsText.setText(control.getSpatial().getName() + "Finished!!! ");
//            } else {
//                wayPointsText.setText(control.getSpatial().getName() + " Reached way point " + wayPointIndex);
//            }
//            
//			wayPointsText.setLocalTranslation((cam.getWidth() - wayPointsText.getLineWidth()) / 2, cam.getHeight(), 0);
//        }
        }});
	
    }

    public void addAGVcar(Vector3f location){
    	AGV = assetManager.loadModel("Models/AGV/AGV.obj" );
    	AGV.setLocalTranslation(location);
    	AGV.scale(10);
    	Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
    	AGV.setMaterial(mat);
    	AGV.setName("Name");
    	nodeAGV = new Node();
    	nodeAGV.attachChild(AGV); // make the AGV appear in the scene  	
    	AllAgvNodes.attachChild(nodeAGV);    }

    public void initMotionControl(){
    	motionControl = new MotionEvent(AGV, path);
        motionControl.setDirectionType(MotionEvent.Direction.Path);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(2f);       
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
    	
    	
    	wayPointsText = new BitmapText(guiFont, false);
        wayPointsText.setSize(guiFont.getCharSet().getRenderedSize());
    	wayPointsText.setSize(guiFont.getCharSet().getRenderedSize());
    	
        path.addListener(new MotionPathListener() {

        public void onWayPointReach(MotionEvent control, int wayPointIndex) {
                if (path.getNbWayPoints() == wayPointIndex + 1) {
                    wayPointsText.setText(control.getSpatial().getName() + "Finished!!! ");
                } else {
                    wayPointsText.setText(control.getSpatial().getName() + " Reached way point " + wayPointIndex);
                }
                //wayPointsText.setLocalTranslation((cam.getWidth() - wayPointsText.getLineWidth()) / 2, cam.getHeight(), 0);
                wayPointsText.setLocalTranslation(100,100,100);
        }});
    }

    public void tempWaypoint(){
    	path = new MotionPath();
        path.addWayPoint(new Vector3f(60, 117, 130));
        path.addWayPoint(new Vector3f(60, 117, 240));
        path.addWayPoint(new Vector3f(124, 117, 240));
        path.addWayPoint(new Vector3f(124, 117, -141));
        path.addWayPoint(new Vector3f(-97, 117, -141));
        path.addWayPoint(new Vector3f(-97, 117, 11));
        path.addWayPoint(new Vector3f(-141, 117, 11));
        path.setCurveTension(0.3f);
        path.enableDebugShape(assetManager, AllAgvNodes);
        
    }
}
