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
	//TODO: Boolean to activate the animation per crane (1 for the RailCrane & 1 for the FreeMovingCrane)
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
    Spatial container;
    float tpf;
	float x = 70f;
	float z = 130f;
    private boolean active2 = true;
    private boolean playing2 = false;
    Vector3f location;
    String sName;
    
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
//    	agv1 = new AGV(new Vector3f(70f,260f,130f), assetManager, allAgvNodes);
//        agv2 = new AGV(new Vector3f(90f,260f,130f), assetManager, allAgvNodes);
        GeometryBatchFactory.optimize(allAgvNodes);
        rootNode.attachChild(allAgvNodes);  
        GeometryBatchFactory.optimize(rootNode); 
    	initScene();
    	initCranes();
    	testContainer();
    	addAllAGVs(location);
        //waypoints code
        guiNode.attachChild(agv1.wayPointsText);
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
    public void simpleUpdate(float tpf) {
    	//Updates the 'Time Per Frame', that's necessary to 
    	//calculate the velocity of certain objects
    	this.tpf = tpf;
    	System.out.println("TPF: " + tpf);
    	
    	storageCrane.animation(tpf);
    	freeMovingCrane.animation(tpf);
    	
    	if(!storageCrane.loseContainer){
    		if(container.getLocalTranslation().y > storageCrane.opslagKraanHook.getLocalTranslation().y && storageCrane.upDown){
    			container.setLocalTranslation(storageCrane.opslagKraanHook.getLocalTranslation());
    		} else if(container.getLocalTranslation().y < storageCrane.opslagKraanHook.getLocalTranslation().y && !storageCrane.upDown)
    			container.setLocalTranslation(storageCrane.opslagKraanHook.getLocalTranslation());
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
    }    
    public void addAllAGVs(Vector3f location){

      	//agv1 = new AGV(new Vector3f(x,260f,z), assetManager, allAgvNodes);
      	for (int i=0; i<100; i++){
      	  	agv1 = new AGV(new Vector3f(x,260f,z), assetManager, allAgvNodes, true, "AVG");
      	  	sName = agv1.Name + i;
      	  	//System.out.println(sName);
      		//z+= 15;
      		x+= 25;
      	}
      }
        
    
    public void testContainer(){
    	container = assetManager.loadModel("Models/container/Container.obj");
        Material container_mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture container_tex = assetManager.loadTexture("Models/container/container.png");
        container_mat.setTexture("DiffuseMap", container_tex);
        container.setMaterial(container_mat);
        container.setLocalTranslation(70,260,50);

        rootNode.attachChild(container);
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
    
    public void initCranes(){
    	freeMovingCrane = new FreeMovingCrane(assetManager,70f,256f,0f);
        rootNode.attachChild(freeMovingCrane.loadModels());
        
        storageCrane = new StorageCrane(assetManager,70f,256f,50f);
        rootNode.attachChild(storageCrane.loadModels());
    }

    private void initInputs() {
        inputManager.addMapping("display_hidePath", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("SwitchPathInterpolation", new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("tensionUp", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping("tensionDown", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("play_stop", new KeyTrigger(KeyInput.KEY_N));
        inputManager.addMapping("play_stop2", new KeyTrigger(KeyInput.KEY_M));
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
                
                if(sName == "AVG99"){
                if (name.equals("play_stop") && keyPressed) {
                    if (playing) {
                        playing = false;
                        agv1.motionControl.stop();
                        System.out.println("AVG NR : " + sName);
                    } else {
                        playing = true;
                        agv1.motionControl.play();
                        System.out.println("AVG NR : " + sName);
                        
                    }
                }
                }
                
                
                
                if (name.equals("play_stop2") && keyPressed) {
                    if (playing2) {
                        playing2 = false;
                        agv1.motionControl2.stop();
                    } else {
                        playing2 = true;
                        agv1.motionControl2.play();
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

        inputManager.addListener(acl, "display_hidePath", "play_stop", "play_stop2", "SwitchPathInterpolation", "tensionUp", "tensionDown");

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