package org.client;

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
import jme3tools.optimize.GeometryBatchFactory;

import org.protocol.Container;
import org.protocol.Protocol;
import org.protocol.ProtocolParser;

import javax.vecmath.Point3d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    
    //Vehicle Spatials
    Spatial seaShip;
    Spatial truck;
    Spatial train;
    Spatial barge;
    
    //Spatials of the Storage Crane
    Spatial stCrane;
    Spatial stSCrane;
    Spatial stHCrane;
    
    //Spatials of the Seaship Crane
    Spatial ssCrane;
    Spatial ssSCrane;
    Spatial ssHCrane;
    
    //Spatials of the Truck Crane
    Spatial tCrane;
    Spatial tSCrane;
    Spatial tHCrane;
    
    //Spatials of the Train Crane
    Spatial trCrane;
    Spatial trSCrane;
    Spatial trHCrane;
    
    Crane [] storageCranes = new Crane [20];
    Crane [] seaShipCranes = new Crane [10];
    Crane [] truckCranes = new Crane [20];
    Crane [] trainCranes = new Crane [4];
    Crane [] bargeCranes = new Crane [8];
    
    Storage storage = new Storage();
    
    public static void main(String[] args){
        Client app = new Client();       
        app.start(); // start the game
    }
    
    @Override
    public void simpleInitApp() {
    	
    	initInputs();
    	//initNifty();
    	
        //agv code
        GeometryBatchFactory.optimize(allAgvNodes);
        rootNode.attachChild(allAgvNodes);  
        GeometryBatchFactory.optimize(rootNode); 
    	initScene();  
    	//testContainer();
    	loadAssets();
    	Storage storage = new Storage();

    	addAllAGVs(location);
    	
        //waypoints code
        c = new networkClient(6666);
    	
        //Cam code
	    cam.setLocation(new Vector3f(0f,260f,0f)); 
	    flyCam.setMoveSpeed(300f);
	    FBC = new FlyByCamera(cam, inputManager);
	    
	    //Protocol Test code
        protocolParser = new ProtocolParser();
        //TODO: Remove this Network test code
        protocolTest();
    }
    
    @Override
    public void simpleUpdate(float tpf) 
    {
    	this.tpf = tpf;
    	
    	for(Crane c : storageCranes)
    	{
    		c.update(tpf);
    	}
    	
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

        //To let the server know we're still alive, server will get confused and presume disconnection
        //when you've hit a breakpoint, but that's why heartbeat timeout @ server is disabled by default
        //c.SendHeartbeat();
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
        
    	int containerCount = 0;
    	for(int y = 0; y < 6; y++)
    	{
    		for(int x = 0; x < 15; x++)
    		{
    			for(int z = 0; z < 20; z++)
    			{
    				if(containerCount < 1800)
    				{
    					String id = String.valueOf(containerCount + 1);
    					Vector3f pos = new Vector3f(xCoord+(x*3),yCoord+(y*2.5f),zCoord-(z*12.7f));
    					Containers cont = new Containers(id, pos, container);
    					container.setLocalTranslation(pos);
    					rootNode.attachChild(cont);
    					
    		    		containerCount++;
    				}
    			}
    		}
    	}
    }
    
    //creates most of the physics and scene logic
    public void initScene(){
    	bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState); 
        Scene scene = new Scene(bulletAppState, assetManager);  //creates a new scene
    	rootNode.attachChild(scene.sceneNode);  //adds the scene to the game
    	viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
    	AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(1.3f));
        rootNode.addLight(al); 
    	
	    waterNode = new Node("Water");
	    Water water = new Water(assetManager, waterNode);  //creates water
	    viewPort.addProcessor(water.fpp);  
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
    	
    	//Initialize Seaship Crane & Barge Crane
    	ssCrane = assetManager.loadModel("Models/crane/zeeKraan.obj");
    	ssSCrane = assetManager.loadModel("Models/crane/zeeKraanSlider.obj");
    	ssHCrane = assetManager.loadModel("Models/crane/zeeKraanHook.obj");
    	Material mat_ssCrane, mat_ssHCrane;
    	mat_ssCrane = new Material( assetManager, "Common/MatDefs/Light/Lighting.j3md");
    	mat_ssHCrane = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    	Texture crane_tex = assetManager.loadTexture("Models/crane/zeekraan.png");
    	mat_ssCrane.setTexture("DiffuseMap", crane_tex);
    	mat_ssHCrane.setColor("Color", ColorRGBA.Black);
    	ssCrane.setMaterial(mat_ssCrane);
    	ssSCrane.setMaterial(mat_ssHCrane);
    	ssHCrane.setMaterial(mat_ssHCrane);
    	
    	//Initialize Truck Crane
    	tCrane = assetManager.loadModel("Models/crane/TruckCrane.obj");
    	tSCrane = assetManager.loadModel("Models/crane/TruckCraneSlider.obj");
    	tHCrane = assetManager.loadModel("Models/crane/TruckCraneHook.obj");
    	Material mat_tCrane, mat_tSCrane;
    	mat_tCrane = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    	mat_tSCrane = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    	mat_tCrane.setColor("Color", ColorRGBA.Green);
    	mat_tSCrane.setColor("Color", ColorRGBA.Black);
    	tCrane.setMaterial(mat_tCrane);
    	tSCrane.setMaterial(mat_tSCrane);
    	tHCrane.setMaterial(mat_tSCrane);
    	
    	//Initialize Train Crane
    	trCrane = assetManager.loadModel("Models/crane/TrainCrane.obj");
    	trSCrane = assetManager.loadModel("Models/crane/TrainCraneSlider.obj");
    	trHCrane = assetManager.loadModel("Models/crane/TrainCraneHook.obj");
    	Material mat_trCrane, mat_trSCrane;
    	mat_trCrane = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    	mat_trSCrane = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    	mat_trCrane.setColor("Color", ColorRGBA.Green);
    	mat_trSCrane.setColor("Color", ColorRGBA.Black);
    	trCrane.setMaterial(mat_trCrane);
    	trSCrane.setMaterial(mat_trSCrane);
    	trHCrane.setMaterial(mat_trSCrane);
    	
    	init_StorageCrane();
    	init_SeaShipCrane();
    	init_TruckCrane();
    	init_TrainCrane();
    	init_BargeCrane();
    	
    	seaShip = assetManager.loadModel("Models/Vehicles/seaShip.obj");
    	truck = assetManager.loadModel("Models/Vehicles/truck.obj");
    	train = assetManager.loadModel("Models/Vehicles/train.obj");
    	barge = assetManager.loadModel("Models/Vehicles/barge.obj");
    	Material mat_vehicles = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    	mat_vehicles.setColor("Color", ColorRGBA.Gray);
    	seaShip.setMaterial(mat_vehicles);
    	truck.setMaterial(mat_vehicles);
    	train.setMaterial(mat_vehicles);
    	barge.setMaterial(mat_vehicles);
    	
    	createVehicle();
    	
    	//TODO: Load other assets in this method, use comments to tell what asset you're loading!
    }
    
    private void init_StorageCrane()
    {
        for (int i = 1; i <= 20; i++) 
        {
            String id = String.valueOf(i);
            Vector3f pos = new Vector3f(-520+(i*60), 255, 715);
            Crane c = new StorageCrane(id, pos, stCrane, stSCrane, stHCrane);
            storageCranes[i - 1] = c;
            rootNode.attachChild(c);
            c.setLocalTranslation(pos);
        }
    }
    
    private void init_SeaShipCrane()
    {
    	for(int i = 1; i <= 10; i++)
    	{
    		String id = String.valueOf(i);
    		Vector3f pos = new Vector3f(-685, 255, 150+(i*50));
    		Crane c = new SeaShipCrane(id, pos, ssCrane, ssSCrane, ssHCrane);
    		seaShipCranes[i-1] = c;
    		rootNode.attachChild(c);
    		c.setLocalTranslation(pos);
    	}
    }
    
    private void init_TruckCrane()
    {
    	for(int i = 1; i <= 20; i++)
    	{
    		String id = String.valueOf(i);
    		Vector3f pos = new Vector3f(250+(i*40), 255, 90);
    		Crane c = new TruckCrane(id, pos, tCrane, tSCrane, tHCrane);
    		truckCranes[i-1] = c;
    		rootNode.attachChild(c);
    		c.setLocalTranslation(pos);
    	}
    }
    
    private void init_TrainCrane()
    {
    	for(int i = 1; i <= 4; i++)
    	{
    		String id = String.valueOf(i);
    		Vector3f pos = new Vector3f(-350+(i*250), 255, 795);
    		Crane c = new TrainCrane(id, pos, trCrane, trSCrane, trHCrane);
    		trainCranes[i-1] = c;
    		rootNode.attachChild(c);
    		c.setLocalTranslation(pos);
    	}
    }
    
    private void init_BargeCrane()
    {
    	for(int i = 1; i <= 8; i++)
    	{
    		String id = String.valueOf(i);
    		Vector3f pos = new Vector3f(-600+(i*100), 255, 25);
    		Crane c = new BargeCrane(id, pos, ssCrane, ssSCrane, ssHCrane);
    		bargeCranes[i-1] = c;
    		rootNode.attachChild(c);
    		c.setLocalTranslation(pos);
    	}
    }
    
    private void createVehicle()
    {
    	Vehicle v = new Train(train);
    	rootNode.attachChild(v);
    }
    
    private void getMessage()
    {
    	Container cont = new Container();
    	int craneType = 1;
    	boolean direction = false;
    	int id = 0;
    	float [] distance = new float []{};
    	
    	float x, y, z;
    	x = (float) cont.getLocation().x;
    	y = (float) cont.getLocation().y;
    	z = (float) cont.getLocation().z;
    	Vector3f conVector = new Vector3f(x, y, z);
    	
    	switch(craneType)
    	{
    		case 1:
    		{
    			for(Crane c : seaShipCranes)
    			{
    				distance = c.distance(conVector);
    			}
    		}
    		case 2:
    		{
    			for(Crane c : truckCranes)
    			{
    				distance = c.distance(conVector);
    			}
    		}
    		case 3:
    		{
    			for(Crane c : seaShipCranes)
    			{
    				distance = c.distance(conVector);
    			}
    		}
    		case 4:
    		{
    			for(Crane c : seaShipCranes)
    			{
    				distance = c.distance(conVector);
    			}
    		}
    		case 5:
    		{
    			//TODO: Another way, because it has to come from an AGV that is parked in a direct line across
    			//from the storageCrane;
    		}
    	}
    	
    	float smallest = 1000;
    	for(int i = 0; i < distance.length; i++)
    	{
    		if(distance[i] < smallest)
    		{
    			smallest = distance[i];
    			id = i;
    		}
    	}

    	Vector3f [] des = new Vector3f [4];
    	
    	switch(craneType)
    	{
    		case 1:
    		{
    			Vector3f startPosCrane = new Vector3f(seaShipCranes[id].getLocalTranslation());
            	Vector3f startPosSlider = new Vector3f(seaShipCranes[id].sliderNode.getLocalTranslation());
            	Vector3f startPosHook = new Vector3f(seaShipCranes[id].hookNode.getLocalTranslation());
    			int count = seaShipCranes.length;
    			
    			//TODO: Do this animation again
//    			des[0] = new Vector3f(startPosCrane.x,startPosCrane.y,startPosCrane.z+((count-id)*10)); //Destination of the crane
//    	    	des[1] = new Vector3f(startPosSlider.x-45-(1*2.5f),startPosSlider.y,startPosSlider.z); //Destination of the slider
//    	    	des[2] = new Vector3f(startPosHook.x,startPosHook.y-(30+(id*2.5f)),startPosHook.z); //Destination of the hook
//    	    	des[3] = new Vector3f(startPosSlider.x,startPosSlider.y,startPosSlider.z); //Destination of the slider back
//    	    	des[4] = new Vector3f(startPosHook.x,startPosHook.y-30,startPosHook.z); //Destination of the hook
    	    	
    			if(direction)
    				seaShipCranes[id].animation(1, des, 0.5f);
    			else
    				seaShipCranes[id].animation(2, des, 0.5f);
    		}
    		case 2:
    		{
            	Vector3f startPosCrane = new Vector3f(truckCranes[id].getLocalTranslation());
            	Vector3f startPosHook = new Vector3f(truckCranes[id].hookNode.getLocalTranslation());
            	
    	    	des[0] = new Vector3f(startPosCrane.x, startPosCrane.y, startPosCrane.z); //Destination of the crane
    	    	des[1] = new Vector3f(startPosHook.x,startPosHook.y-22,startPosHook.z); //Destination of the hook
    	    	des[2] = new Vector3f(startPosCrane.x,startPosCrane.y,startPosCrane.z-50); //Destination of the crane
    	    	
    			if(direction)
    				truckCranes[id].animation(3, des, 0.5f);
    			else
    				truckCranes[id].animation(4, des, 0.5f);
    		}
    		case 3:
    		{
            	Vector3f startPosCrane = new Vector3f(trainCranes[id].getLocalTranslation());
            	Vector3f startPosSlider = new Vector3f(trainCranes[id].sliderNode.getLocalTranslation());
            	Vector3f startPosHook = new Vector3f(trainCranes[id].hookNode.getLocalTranslation());
            	
    	    	des[0] = new Vector3f(conVector.x,startPosCrane.y,startPosCrane.z); //Destination of the crane
    	    	des[1] = new Vector3f(startPosSlider.x,startPosSlider.y,startPosSlider.z+18); //Destination of the slider
    	    	des[2] = new Vector3f(startPosHook.x,startPosHook.y-26,startPosHook.z); //Destination of the hook
    	    	des[3] = new Vector3f(startPosCrane.x,startPosCrane.y,startPosCrane.z); //Destination of the crane
    	    	des[4] = new Vector3f(startPosSlider.x,startPosSlider.y,startPosSlider.z); //Destination of the slider
    	    	des[5] = new Vector3f(startPosHook.x,startPosHook.y-22,startPosHook.z); //Destination of the hook
    	    	
    			if(direction)
    				trainCranes[id].animation(1, des, 0.5f);
    			else
    				trainCranes[id].animation(2, des, 0.5f);
    		}
    		case 4:
    		{
    			//TODO: Barge crane
    			if(direction)
    				bargeCranes[id].animation(1, des, 0.5f);
    			else
    				bargeCranes[id].animation(2, des, 0.5f);
    		}
    		case 5:
    		{
            	Map<String, Vector3f> spot = storage.storageSpots.get("0"); //TODO: Get the right lane of the crane
            	Vector3f spotje = spot.get("254"); //TODO: Get the right spot on that lane
            	
            	Vector3f startPosCrane = new Vector3f(storageCranes[id].getLocalTranslation());
            	Vector3f startPosSlider = new Vector3f(storageCranes[id].sliderNode.getLocalTranslation());
            	Vector3f startPosHook = new Vector3f(storageCranes[id].hookNode.getLocalTranslation());
    			
    			des[0] = new Vector3f(startPosCrane.x,startPosCrane.y,spotje.z); //Destination of the crane
    			des[1] = new Vector3f(startPosSlider.x + spotje.x, startPosSlider.y, startPosSlider.z); //Destination of the slider
    	    	des[2] = new Vector3f(startPosHook.x,startPosHook.y-33,startPosHook.z); //Destination of the hook
            	if(spotje.z > 415) //Destination of the crane
            	{
            		des[3] = new Vector3f(startPosCrane.x,startPosCrane.y,startPosCrane.z);
            		System.out.println("> 415");
            	}
            	else
            	{
            		des[3] = new Vector3f(startPosCrane.x,startPosCrane.y,startPosCrane.z-555);
            		System.out.println("< 415");
            	}
            	des[4] = new Vector3f(startPosSlider.x, startPosSlider.y, startPosSlider.z); //Destination of the slider
    	    	des[5] = new Vector3f(startPosHook.x,startPosHook.y - 33+spotje.y,startPosHook.z); //Destination of the hook
    	    	
    	    	if(direction)
    	    	{
    	    		storageCranes[id].animation(1, des, 0.5f);
    	    	}
    	    	else
    	    	{
    	    		storageCranes[id].animation(2, des, 0.5f);
    	    	}
    		}
    	}
    }

    private void initInputs() {
        inputManager.addMapping("display_hidePath", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("SwitchPathInterpolation", new KeyTrigger(KeyInput.KEY_I));
        if(!FlyByCamera.coordtest){
        inputManager.addMapping("tensionUp", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping("tensionDown", new KeyTrigger(KeyInput.KEY_J));
        }
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
            	
                if (name.equals("startAnimation") && keyPressed) 
                {
                	Vector3f [] des = new Vector3f [6];
                	Vector3f conVector = new Vector3f(830, 264, 802);
                	int id = 0;
                	
                	Vector3f startPosCrane = new Vector3f(trainCranes[id].getLocalTranslation());
                	Vector3f startPosSlider = new Vector3f(trainCranes[id].sliderNode.getLocalTranslation());
                	Vector3f startPosHook = new Vector3f(trainCranes[id].hookNode.getLocalTranslation());
                	
        	    	des[0] = new Vector3f(conVector.x,startPosCrane.y,startPosCrane.z); //Destination of the crane
        	    	des[1] = new Vector3f(startPosSlider.x,startPosSlider.y,startPosSlider.z+18); //Destination of the slider
        	    	des[2] = new Vector3f(startPosHook.x,startPosHook.y-26,startPosHook.z); //Destination of the hook
        	    	des[3] = new Vector3f(startPosCrane.x,startPosCrane.y,startPosCrane.z); //Destination of the crane
        	    	des[4] = new Vector3f(startPosSlider.x,startPosSlider.y,startPosSlider.z); //Destination of the slider
        	    	des[5] = new Vector3f(startPosHook.x,startPosHook.y-22,startPosHook.z); //Destination of the hook
        	    	
        	    	trainCranes[id].animation(1, des, 0.5f);
                }
               
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