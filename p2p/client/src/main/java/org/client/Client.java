package org.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.cinematic.MotionPath;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

import de.lessvoid.nifty.Nifty;

import org.protocol.Container;
import org.protocol.Protocol;
import org.protocol.ProtocolParser;

import jme3tools.optimize.GeometryBatchFactory;

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
	//TODO: Set in logical order!
	//TODO: Boolean to activate the animation per crane (1 for the RailCrane, 1 for the TruckCrane & 1 for the FreeMovingCrane)
	//TODO: Receive the data for calculating the velocity of the animation
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
    FreeMovingCrane freeMovingCrane;
    StorageCrane storageCrane;
    TruckCrane truckCrane;
    Node container;
    float tpf;
	float x = 70f;
	float z = 130f;
    private boolean active2 = true;
    private boolean playing2 = false;
    Vector3f location;
    String sName;
    List<AGV> AGVList;
    Node shipNode;
    int j;
    Crane crane;
    
    //Spatials of the storage crane
    Spatial stCrane;
    Spatial stSCrane;
    Spatial stHCrane;
    
    Crane [] storageCranes = new Crane [20];
    
    public static void main(String[] args){
        Client app = new Client();       
        app.start(); // start the game
    }
    
    @Override
    public void simpleInitApp() {
    	bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState); 
        
    	initInputs();
    	//initNifty();
    	
        //agv code
        GeometryBatchFactory.optimize(allAgvNodes);
        rootNode.attachChild(allAgvNodes);  
        GeometryBatchFactory.optimize(rootNode); 
    	initScene();
    	testShip();   
    	testContainer();
    	loadAssets();

    	addAllAGVs(location);
    	
        //waypoints code
        c = new networkClient(6666);
    	
        //Cam code
	    cam.setLocation(new Vector3f(0f,300f,0f)); 
	    flyCam.setMoveSpeed(300f);
	    FBC = new FlyByCamera(cam, inputManager);
	    
	    //Protocol Test code
        protocolParser = new ProtocolParser();
        //TODO: Remove this Network test code
        protocolTest();
        
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(1.3f));
        rootNode.addLight(al);
    }
    
    @Override
    public void simpleUpdate(float tpf) 
    {
    	this.tpf = tpf;
    	
//    	for(Crane c : storageCranes)
//    	{
//    		c.update(tpf);
//    	}
    	
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
     
    public void addAllAGVs(Vector3f location){
        AGVList = new ArrayList<AGV>();       //agv1 = new AGV(new Vector3f(x,260f,z), assetManager, allAgvNodes);
        for (int i=0; i<100; i++){
         //System.out.println("Test" + i);
        // agv1 = new AGV(new Vector3f(x,260f,z), assetManager, allAgvNodes, true, "AGV");
           AGVList.add(new AGV(new Vector3f(x,260f,z), assetManager, allAgvNodes, true, "AGV" + i));
         x+= 25;
        }
        for(Iterator<AGV> i = AGVList.iterator(); 
          i.hasNext(); ) {
            AGV item = i.next();
            //System.out.println(item);
            //System.out.println("Size " +AGVList.size());
        }
        
       }
         
    //TODO: Put in a class
    public void testContainer(){
    	float xCoord,yCoord,zCoord;
        xCoord = shipNode.getLocalTranslation().x-367;
        yCoord = shipNode.getLocalTranslation().y+220;
        zCoord = shipNode.getLocalTranslation().z+310;
        
    	//Container container = new Container(assetManager, new Vector3f(xCoord, yCoord, zCoord));
        
    	int containerCount = 0;
    	for(int y = 0; y < 6; y++)
    	{
    		for(int x = 0; x < 15; x++)
    		{
    			for(int z = 0; z < 20; z++)
    			{
    				if(containerCount < 1800)
    				{
    					Spatial cont = assetManager.loadModel("Models/container/Container.obj");
    					Material container_mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
    					Texture container_tex = assetManager.loadTexture("Models/container/container.png");
    					container_mat.setTexture("DiffuseMap", container_tex);
    					cont.setMaterial(container_mat);
    					
    					cont.rotate(0,90*FastMath.DEG_TO_RAD,0);
    					cont.setLocalTranslation(xCoord+(x*3),yCoord+(y*2.5f),zCoord-(z*12.7f));
    					rootNode.attachChild(cont);
    					
    		    		containerCount++;
    				}
    			}
    		}
    	}
    }
    
    public void testShip(){
    	SeaShip seaShip = new SeaShip(assetManager,-160,100,150);
    	shipNode = new Node();
    	shipNode.attachChild(seaShip.loadModels());
    	shipNode.scale(2f);
    	shipNode.rotate(0,-4*FastMath.DEG_TO_RAD,0);
    	rootNode.attachChild(shipNode);
    }
    
    //creates most of the physics and scene logic
    public void initScene(){
    	Scene scene = new Scene(bulletAppState, assetManager);  //creates a new scene
    	rootNode.attachChild(scene.sceneNode);  //adds the scene to the game
	    waterNode = new Node("Water");
	    Water water = new Water(assetManager, waterNode);  //creates water
	    viewPort.addProcessor(water.fpp); 
	    viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
	    rootNode.attachChild(waterNode);  //adds water to the world
    }
    
    public void loadAssets()
    {
    	//Initialize Storage Crane
    	stCrane = assetManager.loadModel("Models/crane/storageCrane.obj");
    	stSCrane = assetManager.loadModel("Models/crane/storageCraneSlider.obj");
    	stHCrane = assetManager.loadModel("Models/crane/storageCraneHook.obj");
    	Material mat_stCrane, mat_stHCrane;
    	mat_stCrane = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    	mat_stHCrane = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    	mat_stCrane.setColor("Color", ColorRGBA.Red);
    	mat_stHCrane.setColor("Color", ColorRGBA.Black);
    	stCrane.setMaterial(mat_stCrane);
    	stSCrane.setMaterial(mat_stHCrane);
    	stHCrane.setMaterial(mat_stHCrane);
    	
    	init_StorageCrane();
    	
    	//TODO: Load other assets in this method, use comments to tell what asset you're loading!
    }
    
    private void init_StorageCrane()
    {
        for (int i = 1; i <= 20; i++) 
        {
            String id = String.valueOf(i);
            Vector3f pos = new Vector3f(50+(20*i),260,70);
            Crane c = new StorageCrane(id, pos, stCrane, stSCrane, stHCrane);
            storageCranes[i - 1] = c;
            rootNode.attachChild(c);
            c.setLocalTranslation(pos);
        }
    }

    private void initInputs() {
        inputManager.addMapping("display_hidePath", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("SwitchPathInterpolation", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("tensionUp", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping("tensionDown", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("play_stop", new KeyTrigger(KeyInput.KEY_N));
        inputManager.addMapping("play_stop2", new KeyTrigger(KeyInput.KEY_M));
        inputManager.addMapping("SetWireFrame", new KeyTrigger(KeyInput.KEY_L));
        inputManager.addMapping("startAnimation", new KeyTrigger(KeyInput.KEY_V));
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
            	
//                if (name.equals("startAnimation") && keyPressed) {
//                	storageCrane.setAnimation(0.01, 1, 1);
//                	storageCrane.update();
//                }
               
                if (name.equals("play_stop") && keyPressed) {
                    if (playing) {
                        playing = false;
                      //  AGVList.get(0).motionControl.stop();
                        System.out.println("AGV Index2 : " + j);
                    } 
                    else {
                        playing = true;
                        AGVList.get(j).motionControl.play();
                        System.out.println("AGV Index : " + j);
                        j++;
                    	}
                    }
                
                
                if (name.equals("play_stop2") && keyPressed) {
                    if (playing2) {
                        playing2 = false;
                        AGVList.get(0).motionControl2.stop();
                        //agv1.motionControl2.stop();
                    } else {
                        playing2 = true;
                        AGVList.get(0).motionControl2.play();
                        //agv1.motionControl2.play();
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

        inputManager.addListener(acl, "startAnimation", "display_hidePath", "play_stop", "play_stop2", "SwitchPathInterpolation", "tensionUp", "tensionDown");

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