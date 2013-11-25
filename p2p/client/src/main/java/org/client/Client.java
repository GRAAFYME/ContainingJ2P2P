package org.client;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
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
	public Node waterNode;  //Different nodes have different physics
	private BulletAppState bulletAppState;  //Physics machine
	RigidBodyControl rbc;
	CollisionShape sceneShape;   //gives collisions to the scene
	Spatial sceneModel;
    //Temporary network test
    private Geometry tempContainer;
    //</test>

    Box b;
    Geometry geom;
    private Vector3f walkDirection = new Vector3f();
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    private Boolean sprint = false;
    private networkClient c;
    FlyByCamera FBC;
    private MotionPath path;
    private MotionEvent motionControl;



    public static void main(String[] args){
        Client app = new Client();       
        app.start(); // start the game
    }

    @Override
    public void simpleInitApp() {

        path = new MotionPath();
        path.addWayPoint(new Vector3f(60, 117, 130));
        path.addWayPoint(new Vector3f(60, 117, 240));
        path.addWayPoint(new Vector3f(124, 117, 240));
        path.addWayPoint(new Vector3f(124, 117, -141));
        path.addWayPoint(new Vector3f(-97, 117, -141));
        path.addWayPoint(new Vector3f(-97, 117, 11));
        path.addWayPoint(new Vector3f(-141, 117, 11));
        path.setCurveTension(0.01f);
        path.enableDebugShape(assetManager, rootNode);

    	NiftyMenu niftyMenu = new NiftyMenu();
        stateManager.attach(niftyMenu);

        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/MainMenu.xml", "start");
        guiViewPort.addProcessor(niftyDisplay);
        
        c = new networkClient(6666);
    	initPhysics();
    	Scene scene = new Scene(bulletAppState, assetManager);  //creates a new scene
    	rootNode.attachChild(scene.sceneNode);  //adds the scene to the game
	    waterNode = new Node("Water");
	    Water water = new Water(assetManager, waterNode);  //creates water
	    viewPort.addProcessor(water.fpp); 
	    viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
	    rootNode.attachChild(waterNode);  //adds water to the world
	    cam.setLocation(new Vector3f(0f,150f,0f)); 
	    flyCam.setMoveSpeed(30f);
	    FBC = new FlyByCamera(cam, inputManager);


        protocolParser = new ProtocolParser();
        //TODO: Remove this Network test code
        Box t = new Box(5, 5, 5);
        tempContainer = new Geometry("Box", t);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
        tempContainer.setMaterial(mat);
        rootNode.attachChild(tempContainer);
        }
    
    @Override
    public void simpleUpdate(float tpf) {
    	System.out.println(cam.getLocation());	
        String message = c.getMessages();
        if(message != "")
        {
            System.out.println(message);
            //TODO: deserialize objects (xml->obj)
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

    //creates a blue box for testing
    public void testBox(Vector3f location){
    	Box b = new Box(2, 1, 1); // create cube shape
        Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        geom.setLocalTranslation(location);
        mat.setColor("Color", ColorRGBA.Red);   // set color of material to blue
        geom.setMaterial(mat);                   // set the cube's material
        rootNode.attachChild(geom);              // make the cube appear in the scene	
    }
    
    //creates most of the physics logic
    public void initPhysics(){

    	bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState); 
    }
}