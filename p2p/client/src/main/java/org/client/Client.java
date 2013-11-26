package org.client;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.cinematic.MotionPath;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;
import org.protocol.Container;
import org.protocol.Protocol;
import org.protocol.ProtocolParser;
import javax.vecmath.Point3d;
/*
 * Authors
 * Joshua Bergsma
 * Remco de Bruin
 * Yme van der Graaf
 * Jeffrey Harders
 * Arjen Pander
 * Melinda de Roo 
 * */

public class Client extends SimpleApplication {
	private ProtocolParser protocolParser;
	private Geometry tempContainer; //Temporary network test
	public Node waterNode;  //Different nodes have different physics
	public Node allAgvNodes = new Node();
	private BulletAppState bulletAppState;  //Physics machine
	RigidBodyControl rbc;
	CollisionShape sceneShape;   //gives collisions to the scene
	Spatial sceneModel, AGV, AGV2;
	AGV agv1, agv2;
    Geometry geom;
    private networkClient c;
    FlyByCamera FBC;
    private MotionPath path;
    private boolean active = true;
    private boolean playing = false;
    
    public static void main(String[] args){
        Client app = new Client();       
        app.start(); // start the game
    }
    
    @Override
    public void simpleInitApp() {
    	initInputs();
    	initNifty();
    	initScene();
    	
    	//agv code
    	agv1 = new AGV(new Vector3f(70f,118.5f,130f), assetManager, allAgvNodes);
        agv2 = new AGV(new Vector3f(90f,118.5f,130f), assetManager, allAgvNodes);
        rootNode.attachChild(allAgvNodes);  
        
        //waypoints code
        guiNode.attachChild(agv1.wayPointsText);
        c = new networkClient(6666);
    	
        //Cam code
	    cam.setLocation(new Vector3f(0f,150f,0f)); 
	    flyCam.setMoveSpeed(30f);
	    FBC = new FlyByCamera(cam, inputManager);
	    
	    //Protocol Test code
        protocolParser = new ProtocolParser();
        //TODO: Remove this Network test code
        protocolTest();
        }
    
    @Override
    public void simpleUpdate(float tpf) {
        String message = c.getMessages();
        if(message != "")
        {
            System.out.println(message);
            try {
                Protocol p = protocolParser.deserialize(message);
                Container container = null;
                for (Container c : p.getContainers())
                {
                    container = c;
                    System.out.println(c.getLocation().toString());
                }

                Point3d l = container.getLocation();
                tempContainer.setLocalTranslation((float)l.x, (float)l.y, (float)l.z);

            }
            catch (Exception e)
            {
                System.out.println("Received incorrect package: \n\n" +  e.getMessage());
            }
            
        }
    }    
    //creates most of the physics and scene logic
    public void initScene(){

    	bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState); 
    	Scene scene = new Scene(bulletAppState, assetManager);  //creates a new scene
    	rootNode.attachChild(scene.sceneNode);  //adds the scene to the game
	    waterNode = new Node("Water");
	    Water water = new Water(assetManager, waterNode);  //creates water
	    viewPort.addProcessor(water.fpp); 
	    viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
	    rootNode.attachChild(waterNode);  //adds water to the world
    }

    private void initInputs() {
        inputManager.addMapping("display_hidePath", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("SwitchPathInterpolation", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("tensionUp", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping("tensionDown", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("play_stop", new KeyTrigger(KeyInput.KEY_SPACE));
        ActionListener acl = new ActionListener() {

            public void onAction(String name, boolean keyPressed, float tpf) {
                if (name.equals("display_hidePath") && keyPressed) {
                    if (active) {
                        active = false;
                        path.disableDebugShape();
                    } else {
                        active = true;
                        path.enableDebugShape(assetManager, rootNode);
                    }
                }
                if (name.equals("play_stop") && keyPressed) {
                    if (playing) {
                        playing = false;
                        agv1.motionControl.stop();
                    } else {
                        playing = true;
                        agv1.motionControl.play();
                    }
                }

                if (name.equals("SwitchPathInterpolation") && keyPressed) {
                    if (path.getPathSplineType() == SplineType.CatmullRom){
                        path.setPathSplineType(SplineType.Linear);
                    } else {
                        path.setPathSplineType(SplineType.CatmullRom);
                    }
                }

                if (name.equals("tensionUp") && keyPressed) {
                    path.setCurveTension(path.getCurveTension() + 0.1f);
                    System.err.println("Tension : " + path.getCurveTension());
                }
                if (name.equals("tensionDown") && keyPressed) {
                    path.setCurveTension(path.getCurveTension() - 0.1f);
                    System.err.println("Tension : " + path.getCurveTension());
                }


            }
        };

        inputManager.addListener(acl, "display_hidePath", "play_stop", "SwitchPathInterpolation", "tensionUp", "tensionDown");

    } 

    public void initNifty(){
    	NiftyMenu niftyMenu = new NiftyMenu();
        stateManager.attach(niftyMenu);

        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/MainMenu.xml", "start");
        guiViewPort.addProcessor(niftyDisplay);
    }

    public void protocolTest(){
        Box t = new Box(5, 5, 5);
        tempContainer = new Geometry("Box", t);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
        tempContainer.setMaterial(mat);
        rootNode.attachChild(tempContainer);
    }
}