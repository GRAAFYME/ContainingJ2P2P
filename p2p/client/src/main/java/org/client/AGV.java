package org.client;

import com.bulletphysics.collision.broadphase.Dbvt.Node;
import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.MotionPathListener;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.math.Spline.SplineType;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;



public final class AGV extends Node{
	
	private boolean setWireFrame;
    private MotionPath path;
    private MotionEvent motionControl, motionControl2;
    private boolean active = true;
    private boolean playing = false;
    private boolean playing2 = false;
	private Spatial sceneModel, AutomaticGuidedVehicle, AGV2;
	private AssetManager assetManager;
	private InputManager inputManager;
	private Camera cam;
	private BitmapFont guiFont;
	
    public AGV(AssetManager assetManager, boolean setWireFrame){
    	this.assetManager = assetManager;
    	this.setWireFrame = setWireFrame;
    	//this.path = path;
    	//this.motionControl = motionControl;
    	//this.AutomaticGuidedVehicle = AutomaticGuidedVehicle;
    	//this.AGV2 = AGV2;
    	//this.inputManager = inputManager;
    	CreatePath();
        addAGV(new Vector3f(70f,118.5f,130f));
        addAGV2(new Vector3f(90f,118.5f,130f));
       // this.attachChild(AGV, new Vector3f(70f,118.5f,130f));
        
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
    public void addAGV(Vector3f location){
    	AutomaticGuidedVehicle = assetManager.loadModel("Models/AGV/AGV.obj" );
    	AutomaticGuidedVehicle.setLocalTranslation(location);
    	AutomaticGuidedVehicle.scale(10);
    	Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
        AutomaticGuidedVehicle.setMaterial(mat);
        AutomaticGuidedVehicle.setName("Name");
    	mat.getAdditionalRenderState().setWireframe(this.setWireFrame);
    	
    	
    }
    public void addAGV2(Vector3f location){
    	AGV2 = assetManager.loadModel("Models/AGV/AGV.obj" );
    	AGV2.setLocalTranslation(location);
    	AGV2.scale(10);
    	Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
    	AGV2.setMaterial(mat);
    	AGV2.setName("Name2");
      //  rootNode.attachChild(AGV2);              // make the AGV appear in the scene	
    }
}
