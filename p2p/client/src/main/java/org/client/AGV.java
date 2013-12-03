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
	
	boolean setWireFrame;
    public MotionPath path, path2;
    public MotionEvent motionControl, motionControl2;
	private Spatial AGV, AGV2;
	public BitmapText wayPointsText;
	private AssetManager assetManager;
	private BitmapFont guiFont;
	public Node nodeAGV;
	private Node AllAgvNodes;
	Vector3f location;
	String Name;


	
	public AGV(Vector3f location, AssetManager am, Node allAgvNodes, boolean setWireFrame, String Name){
		this.AllAgvNodes = allAgvNodes;
		this.assetManager = am;
		this.setWireFrame = setWireFrame;
		this.Name = Name;
		MotionPath1();
		addAGVcar(location);
		//addAGVcar2(location);
		initMotionControl();
	}

    public void addAGVcar(Vector3f location){
    	
    	AGV = assetManager.loadModel("Models/AGV/AGV.obj" );
    	AGV.setLocalTranslation(location);
    	AGV.scale(10);
    	Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
    	AGV.setMaterial(mat);	
    	AGV.setName(Name);
    	mat.getAdditionalRenderState().setWireframe(setWireFrame);
    	nodeAGV = new Node();
    	nodeAGV.attachChild(AGV); // make the AGV appear in the scene  	
    	AllAgvNodes.attachChild(nodeAGV); 
    	}
 
    
    public void addAGVcar2(Vector3f location){
    	AGV2 = assetManager.loadModel("Models/AGV/AGV.obj" );
    	AGV2.setLocalTranslation(location);
    	AGV2.scale(10);
    	Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
    	AGV2.setMaterial(mat);
    	AGV2.setName("Name2");
    	nodeAGV = new Node();
    	nodeAGV.attachChild(AGV2); // make the AGV appear in the scene  	
    	AllAgvNodes.attachChild(nodeAGV);   
    	}

    public void initMotionControl(){
    	motionControl = new MotionEvent(AGV, path);
        motionControl.setDirectionType(MotionEvent.Direction.Path);
        motionControl.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
        motionControl.setInitialDuration(10f);
        motionControl.setSpeed(2f);       
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        

    	
//    	motionControl2 = new MotionEvent(AGV2, path);
//        motionControl2.setDirectionType(MotionEvent.Direction.Path);
//        motionControl2.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
//        motionControl2.setInitialDuration(10f);
//        motionControl2.setSpeed(2f);  
    	
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
                if (path.getNbWayPoints() == wayPointIndex+ 1){
                	MotionPath2();
                	
                	motionControl2 = new MotionEvent(AGV, path2);
                    motionControl2.setDirectionType(MotionEvent.Direction.Path);
                    motionControl2.setRotation(new Quaternion().fromAngleNormalAxis(-FastMath.HALF_PI, Vector3f.UNIT_Y));
                    motionControl2.setInitialDuration(10f);
                    motionControl2.setSpeed(2f);       
                    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
                	motionControl2.play();
                }
                //wayPointsText.setLocalTranslation((cam.getWidth() - wayPointsText.getLineWidth()) / 2, cam.getHeight(), 0);
                wayPointsText.setLocalTranslation(100,100,100);
        }});
    }

    public void MotionPath1(){
    	path = new MotionPath();
        path.addWayPoint(new Vector3f(60, 260f, 130));
        path.addWayPoint(new Vector3f(60, 260f, 240));
        path.addWayPoint(new Vector3f(124, 260f, 240));
        path.addWayPoint(new Vector3f(124, 260f, -141));
        path.addWayPoint(new Vector3f(-97, 260f, -141));
        path.addWayPoint(new Vector3f(-97, 260f, 11));
        path.addWayPoint(new Vector3f(150, 260f, 11));
        path.addWayPoint(new Vector3f(200, 260f, 11));
        path.setCurveTension(0.3f);
        path.enableDebugShape(assetManager, AllAgvNodes);
        
    }
    
    public void MotionPath2(){
    	path2 = new MotionPath();
        path2.addWayPoint(new Vector3f(210, 260f, 11));
        path2.addWayPoint(new Vector3f(240, 260f, 50));
        path2.addWayPoint(new Vector3f(260, 260f, 80));
        path2.addWayPoint(new Vector3f(500, 260f, 250));
        path2.setCurveTension(0.3f);
        path2.enableDebugShape(assetManager, AllAgvNodes);
        
    }
}
