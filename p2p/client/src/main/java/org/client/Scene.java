package org.client;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Scene {
	public Node sceneNode;
<<<<<<< HEAD
	private BulletAppState bulletAppState;  //Physics machine
	RigidBodyControl rigidBodyControl;
	CollisionShape sceneShape;   //gives collisions to the scene
=======
	private BulletAppState bulletAppState; // Physics machine
	RigidBodyControl rbc;
	CollisionShape sceneShape; // gives collisions to the scene
>>>>>>> b33be5d4798a315c1042a3eee8087ef10bded7cc
	Spatial sceneModel;
	AssetManager assetManager;
	public static PointLight sunLight;
	public static AmbientLight ambient;
<<<<<<< HEAD
	
	//constructor
	public Scene(BulletAppState Bull, AssetManager am){
	    assetManager = am;
	    bulletAppState = Bull;
	    sceneNode = new Node();
	    initScene();
	}   	
	//creates and loads the scene    
    public void initScene(){
    	//sceneModel = assetManager.loadModel("/Scenes/PlatformScene.j3o");
    	sceneModel = assetManager.loadModel("/Models/platformmodel/platformmodel.j3o");
        sceneShape = CollisionShapeFactory.createMeshShape(sceneModel);
        rigidBodyControl = new RigidBodyControl(sceneShape, 0);
        sceneNode.attachChild(sceneModel);   //adds the sceneModel to the world
        sceneModel.addControl(rigidBodyControl); 
        bulletAppState.getPhysicsSpace().add(rigidBodyControl); 
        initLight();  
    }
    //creates and loads the light
    public void initLight(){
    	sunLight = new PointLight();
        sunLight.setPosition(new Vector3f(650, 2000, 40));
        sunLight.setColor(ColorRGBA.White.mult(1f));
        sunLight.setRadius(3000f);
        
        ambient = new AmbientLight();  //creates a light in the scene, which lights everything from all angles
        ambient.setColor(ColorRGBA.White.mult(5f));   
    }
=======

	// constructor
	public Scene(BulletAppState Bull, AssetManager am) {
		assetManager = am;
		bulletAppState = Bull;
		sceneNode = new Node();
		initScene();
	}

	// creates and loads the scene
	public void initScene() {
		// sceneModel = assetManager.loadModel("/Scenes/PlatformScene.j3o");
		sceneModel = assetManager
				.loadModel("/Models/platformmodel/platformmodel.j3o");
		sceneShape = CollisionShapeFactory.createMeshShape(sceneModel);
		rbc = new RigidBodyControl(sceneShape, 0);
		sceneNode.attachChild(sceneModel); // adds the sceneModel to the world
		sceneModel.addControl(rbc);
		bulletAppState.getPhysicsSpace().add(rbc);
		initLight();
	}

	// creates and loads the light
	public void initLight() {
		sunLight = new PointLight();
		sunLight.setPosition(new Vector3f(650, 2000, 40));
		sunLight.setColor(ColorRGBA.White.mult(1f));
		sunLight.setRadius(3000f);

		ambient = new AmbientLight(); // creates a light in the scene, which
										// lights everything from all angles
		ambient.setColor(ColorRGBA.White.mult(5f));
	}
>>>>>>> b33be5d4798a315c1042a3eee8087ef10bded7cc
}
