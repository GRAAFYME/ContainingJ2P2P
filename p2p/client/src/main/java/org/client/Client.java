package org.client;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
//import com.jme3.bullet.collision.shapes.CollisionShape;
//import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.cinematic.MotionPath;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Spline.SplineType;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.BatchNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

import de.lessvoid.nifty.Nifty;
import jme3tools.optimize.GeometryBatchFactory;

import org.protocol.Container;
import org.protocol.Protocol;
import org.protocol.ProtocolParser;

import javax.vecmath.Point3d;

import java.util.ArrayList;
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

public class Client extends SimpleApplication 
{
    float tpf;
	
	//Protocol variables
//	private ProtocolParser protocolParser;
//	private networkClient c;
	
	//Scene
	Spatial sceneModel;
	BatchNode batch = new BatchNode();
	public Node waterNode;  //Different nodes have different physics
	//private RigidBodyControl rbc;
	//private CollisionShape sceneShape;   //gives collisions to the scene
	private BulletAppState bulletAppState;  //Physics machine
    FlyByCamera FBC;
	
	//Container
	public Spatial container;
	List<Containers> containerList; 
	
	//AGV
    private Spatial AGV, AGV2;
	private AGV agv1, agv2;
    Vector3f location;
	float x1 = -470f;
	float z1 = 150f;
	float x2 = -470f;
	float z2 = 735f;
	private int j;
	private Node allAgvNodes = new Node();
    private boolean active = true;
    private boolean playing = false;
    //private boolean active2 = true;
    private boolean playing2 = false;
	private MotionPath path;
	private boolean setWireFrame = false;

	//Motionpaths
    MotionPaths mp;
    
    //Crane
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
    
    Crane [] storageCranes = new Crane [24];
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
    	loadAssets();
    	testContainer();

    	addAllAGVs(location);
    	
        //waypoints code
        //c = new networkClient(6666);
    	
        //Cam code
	    cam.setLocation(new Vector3f(0f,260f,0f)); 
	    flyCam.setMoveSpeed(300f);
	    FBC = new FlyByCamera(cam, inputManager);
	    mp = new MotionPaths(assetManager, allAgvNodes);
	    
	    //Protocol Test code
        //protocolParser = new ProtocolParser();
    }
    
    @Override
    public void simpleUpdate(float tpf) 
    {
    	this.tpf = tpf;
    	
    	for(Crane c : storageCranes)
    	{
    		c.update(tpf);
    	}
    	//System.out.println(cam.getLocation());
//        String message = c.getMessages();
//        if(message != "")
//        {
//            System.out.println(message);
//            try {
//                Protocol p = protocolParser.deserialize(message);
//                Container container;
//                for (Container c : p.getContainers())
//                {
//                    container = c;
//                    System.out.println(c.getLocation().toString());
//                }
//                
//                //Point3d l = container.getLocation();
//                
//            }
//            catch (Exception e)
//            {
//                System.out.println("Received incorrect package: \n\n" +  e.getMessage());
//            }
//        }

        //To let the server know we're still alive, server will get confused and presume disconnection
        //when you've hit a breakpoint, but that's why heartbeat timeout @ server is disabled by default
        //c.SendHeartbeat();
    }
     
    public void addAllAGVs(Vector3f location)
    {
        for (int i = 0; i < 50; i++)
        {
        	location = new Vector3f(x1, 260, z1);
        	agv1 = new AGV(String.valueOf(i), location, AGV, "AGV" + i);
        	x1 += 25;
        	rootNode.attachChild(agv1);
        	agv1.setLocalTranslation(location);
        }
        
    	for (int j = 0; j < 50; j++)
    	{
    		location = new Vector3f(x2, 260, z2);
    		agv2 = new AGV(String.valueOf(j), location, AGV2, "AGV" + j);
    		x2 += 25;
    		rootNode.attachChild(agv2);
        	agv2.setLocalTranslation(location);
    	}    
    }
         
    //TODO: Create them when they need to be created
    public void testContainer()
    {
    	containerList = new ArrayList<Containers>();
    	float xCoord,yCoord,zCoord;
        xCoord = seaShip.getLocalTranslation().x-367;
        yCoord = seaShip.getLocalTranslation().y+220;
        zCoord = seaShip.getLocalTranslation().z+310;
        
        
        
        
        
        
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
    					Vector3f pos = new Vector3f(xCoord+(x*2.4f),yCoord+(y*2.5f),zCoord-(z*12.3f));
   					    //Containers cont = new Containers(id, pos, container);
    					containerList.add(new Containers(id, pos, container));
    					container.setLocalTranslation(pos);
    					//rootNode.attachChild(cont);
    					//rootNode.attachChild(containerList.get(containerCount));
    					batch.attachChild(containerList.get(containerCount));
    					containerCount++;
    				}
    			}
    		}
    	}
    	rootNode.attachChild(batch);
    	batch.batch();
    }
    
    //creates most of the physics and scene logic
    public void initScene(){
    	bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState); 
        Scene scene = new Scene(bulletAppState, assetManager);  //creates a new scene
    	rootNode.attachChild(scene.sceneNode);  //adds the scene to the game
    	viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
        rootNode.addLight(Scene.sunLight); //adds the light to the world. 
        rootNode.addLight(Scene.ambient); //adds the light to the world. 
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
    	
    	container = assetManager.loadModel("Models/container/Container.obj");
    	Material mat_container;
    	mat_container = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
    	Texture cont_tex = assetManager.loadTexture("Models/container/container.png");
    	mat_container.setTexture("DiffuseMap", cont_tex);
    	container.setMaterial(mat_container);
    	
    	AGV = assetManager.loadModel("Models/AGV/AGV.obj");
    	AGV2 = assetManager.loadModel("Models/AGV/AGV.obj");
    	Material mat_agv = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
    	mat_agv.getAdditionalRenderState().setWireframe(setWireFrame);
    	AGV.setMaterial(mat_agv);
    	AGV2.setMaterial(mat_agv);
    	
    	createVehicle();
    }
    
    private void init_StorageCrane()
    {
        for (int i = 1; i <= 24; i++) 
        {
            String id = String.valueOf(i);
            Vector3f pos = new Vector3f(-520+(i*60), 255, 670);
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
    	Vehicle v = new SeaShip(seaShip);
    	rootNode.attachChild(v);
    }
    
    private void getMessage()
    {
    	Container cont = new Container();
    	int craneType = 1; //TODO: Send from protocol
    	boolean direction = false; //TODO: Send from protocol
    	int id = 0; //Chosen later
    	float [] distance = new float []{}; 	
    	float smallest = 1000;
    	
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
    			
    	    	for(int i = 0; i < distance.length; i++)
    	    	{
    	    		if(distance[i] < smallest)
    	    		{
    	    			if(!seaShipCranes[i].isBusy())
    	    			{
    	    				smallest = distance[i];
    	    				id = i;
    	    			}
    	    		}
    	    	}
    		}
    		case 2:
    		{
    			for(Crane c : truckCranes)
    			{
    				distance = c.distance(conVector);
    			}
    			
    	    	for(int i = 0; i < distance.length; i++)
    	    	{
    	    		if(distance[i] < smallest)
    	    		{
    	    			if(!truckCranes[i].isBusy())
    	    			{
    	    				smallest = distance[i];
    	    				id = i;
    	    			}
    	    		}
    	    	}
    		}
    		case 3:
    		{
    			for(Crane c : trainCranes)
    			{
    				distance = c.distance(conVector);
    			}
    			
    	    	for(int i = 0; i < distance.length; i++)
    	    	{
    	    		if(distance[i] < smallest)
    	    		{
    	    			if(!trainCranes[i].isBusy())
    	    			{
    	    				smallest = distance[i];
    	    				id = i;
    	    			}
    	    		}
    	    	}
    		}
    		case 4:
    		{
    			for(Crane c : bargeCranes)
    			{
    				distance = c.distance(conVector);
    			}
    			
    	    	for(int i = 0; i < distance.length; i++)
    	    	{
    	    		if(distance[i] < smallest)
    	    		{
    	    			if(!bargeCranes[i].isBusy())
    	    			{
    	    				smallest = distance[i];
    	    				id = i;
    	    			}
    	    		}
    	    	}
    		}
    		case 5:
    		{
    			//TODO: Check if there is an AGV at the beginning point
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
            	
            	des[0] = new Vector3f(startPosCrane.x,startPosCrane.y,conVector.z); //Destination of the crane
    	    	des[1] = new Vector3f(startPosSlider.x-45-(1*2.5f),startPosSlider.y,startPosSlider.z); //Destination of the slider
    	    	des[2] = new Vector3f(startPosHook.x,startPosHook.y-(30+(id*2.5f)),startPosHook.z); //Destination of the hook
    	    	des[3] = new Vector3f(startPosCrane.x,startPosCrane.y,conVector.z); //Destination of the crane
    	    	des[4] = new Vector3f(startPosSlider.x,startPosSlider.y,startPosSlider.z); //Destination of the slider back
    	    	des[5] = new Vector3f(startPosHook.x,startPosHook.y-30,startPosHook.z); //Destination of the hook
    	    	
    			if(direction)
    				seaShipCranes[id].animation(1, des, 5);
    			else
    				seaShipCranes[id].animation(2, des, 5);
    		}
    		case 2:
    		{
            	Vector3f startPosCrane = new Vector3f(truckCranes[id].getLocalTranslation());
            	Vector3f startPosHook = new Vector3f(truckCranes[id].hookNode.getLocalTranslation());
            	
    	    	des[0] = new Vector3f(startPosCrane.x, startPosCrane.y, startPosCrane.z); //Destination of the crane
    	    	des[1] = new Vector3f(startPosHook.x,startPosHook.y-22,startPosHook.z); //Destination of the hook
    	    	des[2] = new Vector3f(startPosCrane.x,startPosCrane.y,startPosCrane.z-50); //Destination of the crane
    	    	
    			if(direction)
    				truckCranes[id].animation(3, des, 5);
    			else
    				truckCranes[id].animation(4, des, 5);
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
    				trainCranes[id].animation(1, des, 5);
    			else
    				trainCranes[id].animation(2, des, 5);
    		}
    		case 4:
    		{
            	Vector3f startPosCrane = new Vector3f(bargeCranes[id].getLocalTranslation());
            	Vector3f startPosSlider = new Vector3f(bargeCranes[id].sliderNode.getLocalTranslation());
            	Vector3f startPosHook = new Vector3f(bargeCranes[id].hookNode.getLocalTranslation());
            	
            	des[0] = new Vector3f(conVector.x,startPosCrane.y,startPosCrane.z); //Destination of the crane
    	    	des[1] = new Vector3f(startPosSlider.x,startPosSlider.y,startPosSlider.z-45-(1*2.5f)); //Destination of the slider
    	    	des[2] = new Vector3f(startPosHook.x,startPosHook.y-(30+(id*2.5f)),startPosHook.z); //Destination of the hook
    	    	des[3] = new Vector3f(conVector.x,startPosCrane.y,startPosCrane.z); //Destination of the crane
    	    	des[4] = new Vector3f(startPosSlider.x,startPosSlider.y,startPosSlider.z); //Destination of the slider back
    	    	des[5] = new Vector3f(startPosHook.x,startPosHook.y-30,startPosHook.z); //Destination of the hook
    	    	
    			if(direction)
    				bargeCranes[id].animation(1, des, 5);
    			else
    				bargeCranes[id].animation(2, des, 5);
    		}
    		case 5:
    		{
    			//TODO: Use the sorting function to get the right lane and spot on the storage
            	Map<String, Vector3f> spot = storage.storageSpots.get("0"); //TODO: Get the right lane of the crane
            	Vector3f spotje = spot.get("254"); //TODO: Get the right spot on that lane
            	
            	Vector3f startPosCrane = new Vector3f(storageCranes[id].getLocalTranslation());
            	Vector3f startPosSlider = new Vector3f(storageCranes[id].sliderNode.getLocalTranslation());
            	Vector3f startPosHook = new Vector3f(storageCranes[id].hookNode.getLocalTranslation());
            	
            	if(spotje.z > 370) //Destination of the crane
            	{
            		des[0] = new Vector3f(startPosCrane.x,startPosCrane.y,startPosCrane.z);
            	}
            	else
            	{
            		des[0] = new Vector3f(startPosCrane.x,startPosCrane.y,startPosCrane.z-465);
            	}
    			des[1] = new Vector3f(startPosSlider.x + spotje.x, startPosSlider.y, startPosSlider.z); //Destination of the slider
    	    	des[2] = new Vector3f(startPosHook.x,startPosHook.y-33,startPosHook.z); //Destination of the hook
    			des[3] = new Vector3f(startPosCrane.x,startPosCrane.y,spotje.z); //Destination of the crane
            	des[4] = new Vector3f(startPosSlider.x, startPosSlider.y, startPosSlider.z); //Destination of the slider
    	    	des[5] = new Vector3f(startPosHook.x,startPosHook.y - 33+spotje.y,startPosHook.z); //Destination of the hook
    	    	
    	    	if(direction)
    	    	{
    	    		storageCranes[id].animation(1, des, 5);
    	    	}
    	    	else
    	    	{
    	    		storageCranes[id].animation(2, des, 5);
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
        inputManager.addMapping("speedDown", new KeyTrigger(KeyInput.KEY_E));
        inputManager.addMapping("speedUp", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addMapping("speedReset", new KeyTrigger(KeyInput.KEY_R));
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
                
                if (name.equals("speedDown")&& keyPressed){
                speed = speed / 2;
                System.out.println(speed);
                	
                } else {
                if (name.equals("speedUp")&& keyPressed){                
                speed = speed * 2;
                System.out.println(speed);
                	
                } else{
                if (name.equals("speedReset")&& keyPressed){
                speed = 1;
                System.out.println(speed);
                	
                }
                }
                }
            	
                if (name.equals("startAnimation") && keyPressed) 
                {
                	Vector3f [] des = new Vector3f [6];
                	//Vector3f conVector = new Vector3f(200, 264, 802);
                	int id = 0;
                	
                	Map<String, Vector3f> spot = storage.storageSpots.get("0");
                	Vector3f spotje = spot.get("254");
                	
                	Vector3f startPosCrane = new Vector3f(storageCranes[id].getLocalTranslation());
                	Vector3f startPosSlider = new Vector3f(storageCranes[id].sliderNode.getLocalTranslation());
                	Vector3f startPosHook = new Vector3f(storageCranes[id].hookNode.getLocalTranslation());
                	
                	if(spotje.z > 395) //Destination of the crane
                	{
                		des[0] = new Vector3f(startPosCrane.x,startPosCrane.y,startPosCrane.z);
                		System.out.println("> 370");
                	}
                	else
                	{
                		des[0] = new Vector3f(startPosCrane.x,startPosCrane.y,startPosCrane.z-465);
                		System.out.println("< 370");
                	}
        			des[1] = new Vector3f(startPosSlider.x + spotje.x, startPosSlider.y, startPosSlider.z); //Destination of the slider
        	    	des[2] = new Vector3f(startPosHook.x,startPosHook.y-33,startPosHook.z); //Destination of the hook
        			des[3] = new Vector3f(startPosCrane.x,startPosCrane.y,spotje.z); //Destination of the crane
                	des[4] = new Vector3f(startPosSlider.x, startPosSlider.y, startPosSlider.z); //Destination of the slider
        	    	des[5] = new Vector3f(startPosHook.x,startPosHook.y - 33+spotje.y,startPosHook.z); //Destination of the hook
        	    	
        	    	storageCranes[id].animation(1, des, 0.5f);
                }
               
                if (name.equals("play_stop") && keyPressed) {
                    if (playing) {
                        playing = false;
                      //  AGVList.get(0).motionControl.stop();
                        System.out.println("AGV Index2 : " + j);
                    } 
                    else {
                        playing = true;
                      //  AGVList.get(j).motionControl.play();
                        System.out.println("AGV Index : " + j);
                        j++;
                    	}
                    }
                
                
                if (name.equals("play_stop2") && keyPressed) {
                    if (playing2) {
                        playing2 = false;
                     //   AGVList.get(0).motionControl2.stop();
                        //agv1.motionControl2.stop();
                    } else {
                        playing2 = true;
                     //   AGVList.get(0).motionControl2.play();
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

        inputManager.addListener(acl, "startAnimation", "display_hidePath", "play_stop", "play_stop2", "SwitchPathInterpolation", "tensionUp", "tensionDown", "speedUp", "speedDown", "speedReset");

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
}