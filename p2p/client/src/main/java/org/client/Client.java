package org.client;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
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
	BulletAppState bulletAppState;  //Physics machine
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
    	testBox();
        waterNode = new Node("Water");
        Water water = new Water(assetManager, waterNode);
        water.initPPcWater();
        viewPort.addProcessor(water.fpp); 
        rootNode.attachChild(waterNode);  //adds water to the world
    	flyCam.setMoveSpeed(10f);         // sets the speed at which the cam can fly around.
        cam.setLocation(new Vector3f(0f,15f,0f));   // changes the starting location of the fly cam
    	viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));     //sets the sky to a blue color.
        }
    
    //creates a blue box for testing
    public void testBox(){
    	Box b = new Box(1, 1, 1); // create cube shape
        Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
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

}