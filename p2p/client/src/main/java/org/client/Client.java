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
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.ui.Picture;
import com.jme3.water.WaterFilter;
import java.math.BigDecimal;
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

    public static void main(String[] args){
        Client app = new Client();
        app.start(); // start the game
    }

    @Override
    public void simpleInitApp() {
    	initPhysics();
    	initScene();
	    initLight();
	    
	    waterNode = new Node("Water");
	    Water water = new Water(assetManager, waterNode);
	    water.initPPcWater();
	    viewPort.addProcessor(water.fpp); 
	    rootNode.attachChild(waterNode);  //adds water to the world
	    cam.setLocation(new Vector3f(0f,150f,0f)); 
	    flyCam.setMoveSpeed(10f);
        }
    
    @Override
    public void simpleUpdate(float tpf) {
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
        
    }
    
    public void initLight(){
        AmbientLight ambient = new AmbientLight();  //creates a light in the scene, which lights everything from all angles
        ambient.setColor(ColorRGBA.White.mult(4f));
        rootNode.addLight(ambient);     //adds the light to the world. 
        
        PointLight cornerLight1 = new PointLight();
        cornerLight1.setPosition(new Vector3f(-490, 300, 500));
        cornerLight1.setColor(ColorRGBA.White.mult(.25f));
        cornerLight1.setRadius(2000f);
        rootNode.addLight(cornerLight1); 
        
        PointLight cornerLight2 = new PointLight();
        cornerLight2.setPosition(new Vector3f(-490, 300, -500));
        cornerLight2.setColor(ColorRGBA.White.mult(.25f));
        cornerLight2.setRadius(2000f);
        rootNode.addLight(cornerLight2); 
        
        PointLight cornerLight3 = new PointLight();
        cornerLight3.setPosition(new Vector3f(500, 300, -500));
        cornerLight3.setColor(ColorRGBA.White.mult(.25f));
        cornerLight3.setRadius(2000f);
        rootNode.addLight(cornerLight3); 
        
        PointLight cornerLight4 = new PointLight();
        cornerLight4.setPosition(new Vector3f(500, 300, 500));
        cornerLight4.setColor(ColorRGBA.White.mult(.25f));
        cornerLight4.setRadius(2000f);
        rootNode.addLight(cornerLight4); 
        
        PointLight sunLight = new PointLight();
        sunLight.setPosition(new Vector3f(0, 500, 0));
        sunLight.setColor(ColorRGBA.White.mult(.25f));
        sunLight.setRadius(2000f);
        rootNode.addLight(sunLight); 
    }

}