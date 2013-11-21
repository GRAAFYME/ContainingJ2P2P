package org.client;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.scene.Node;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.math.Vector3f;
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

public class Client extends SimpleApplication {
	
	public Node waterNode;  //Different nodes have different physics
	private BulletAppState bulletAppState;  //Physics machine
	RigidBodyControl rbc;
	CollisionShape sceneShape;   //gives collisions to the scene
	Spatial sceneModel;
    Box b;
    Geometry geom;
    private Vector3f walkDirection = new Vector3f();
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    CameraSettings cs = new CameraSettings();
    private CharacterControl player;
    private Boolean sprint = false;

	
	
	
    public static void main(String[] args){
        Client app = new Client();
        app.start(); // start the game
    }

    @Override
    public void simpleInitApp() {
    	initPhysics();
    	initScene();
    	testBox();
       // waterNode = new Node("Water");
       // Water water = new Water(assetManager, waterNode);
       // water.initPPcWater();
        //viewPort.addProcessor(water.fpp); 
       // rootNode.attachChild(waterNode);  //adds water to the world
    	//flyCam.setMoveSpeed(10f);         // sets the speed at which the cam can fly around.
      //  cam.setLocation(new Vector3f(0f,15f,0f));   // changes the starting location of the fly cam
    	//viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));     //sets the sky to a blue color.
    	
        //setDisplayStatView(false);
        //setDisplayFps(false);
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.getPhysicsSpace();
        player = CameraSettings.MyCharacter(this, rootNode, bulletAppState.getPhysicsSpace());  
        b = new Box(Vector3f.ZERO, 2, 1, 1);
        geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        geom.setMaterial(mat);
        mat.getAdditionalRenderState().setWireframe(true);
        rootNode.attachChild(geom);
        //Cam rotation, starting direction
        Quaternion q = new Quaternion();
        q.fromAngles(0, 120, 0);
        cam.setRotation(q);
        }
    
    @Override
    public void simpleUpdate(float tpf) {
        camDir.set(cam.getDirection()).multLocal(0.9f);
        camLeft.set(cam.getLeft()).multLocal(0.4f);
        camDir.y = 0;
        camLeft.y = 0;
        walkDirection.set(0, 0, 0);
        if (cs.left()) {
            walkDirection.addLocal(camLeft);
        }
        if (cs.right()) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (cs.up()) {
            walkDirection.addLocal(camDir);
        }
        if (cs.down()) {
            walkDirection.addLocal(camDir.negate());
        }
        if (sprint == false) {

        }
        if (cs.sprint() && cs.up()) {
            sprint = true;
        }
        if (sprint == true) {
            flyCam.setMoveSpeed(500);
            walkDirection.addLocal(camDir);

        }

        player.setWalkDirection(walkDirection);
        cam.setLocation(player.getPhysicsLocation());
    }

    
    //creates a blue box for testing
    public void testBox(){
    	Box b = new Box(2, 1, 1); // create cube shape
        Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat.setColor("Color", ColorRGBA.Red);   // set color of material to blue
        geom.setMaterial(mat);                   // set the cube's material
        rootNode.attachChild(geom);              // make the cube appear in the scene	
    }
    
    //creates most of the physics logic
    public void initPhysics(){
    	bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState); 
    }
    //creates and loads the scene
    public void initScene(){
    	sceneModel = assetManager.loadModel("/Scenes/ClientScene.j3o");
        sceneShape = CollisionShapeFactory.createMeshShape(sceneModel);
        rbc = new RigidBodyControl(sceneShape, 0);
        rootNode.attachChild(sceneModel);   //adds the sceneModel to the world
        sceneModel.addControl(rbc); 
        bulletAppState.getPhysicsSpace().add(rbc); 
        
        AmbientLight ambient = new AmbientLight();  //creates a light in the scene, which lights everything from all angles
        ambient.setColor(ColorRGBA.White.mult(4f));
        rootNode.addLight(ambient);     //adds the light to the world. 
    }
    
    public void collision(PhysicsCollisionEvent event) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}