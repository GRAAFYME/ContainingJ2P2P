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

public class Scene {
	public Node sceneNode;
	private BulletAppState bulletAppState;  //Physics machine
	RigidBodyControl rbc;
	CollisionShape sceneShape;   //gives collisions to the scene
	Spatial sceneModel;
	AssetManager assetManager;
	
	//constructor
	public Scene(BulletAppState Bull, AssetManager am){
	    assetManager = am;
	    bulletAppState = Bull;
	    sceneNode = new Node();
	    initScene();
	}   	
	
    //creates and loads the scene    
    public void initScene(){
    	sceneModel = assetManager.loadModel("/Scenes/ClientScene.j3o");
        sceneShape = CollisionShapeFactory.createMeshShape(sceneModel);
        rbc = new RigidBodyControl(sceneShape, 0);
        sceneNode.attachChild(sceneModel);   //adds the sceneModel to the world
        sceneModel.addControl(rbc); 
        bulletAppState.getPhysicsSpace().add(rbc); 
        initLight();  
    }
    
    //creates and loads the light
    public void initLight(){
        AmbientLight ambient = new AmbientLight();  //creates a light in the scene, which lights everything from all angles
        ambient.setColor(ColorRGBA.White.mult(4f));
        sceneNode.addLight(ambient);     //adds the light to the world. 
        
        PointLight cornerLight1 = new PointLight();
        cornerLight1.setPosition(new Vector3f(-490, 300, 500));
        cornerLight1.setColor(ColorRGBA.White.mult(.25f));
        cornerLight1.setRadius(2000f);
        sceneNode.addLight(cornerLight1); 
        
        PointLight cornerLight2 = new PointLight();
        cornerLight2.setPosition(new Vector3f(-490, 300, -500));
        cornerLight2.setColor(ColorRGBA.White.mult(.25f));
        cornerLight2.setRadius(2000f);
        sceneNode.addLight(cornerLight2); 
        
        PointLight cornerLight3 = new PointLight();
        cornerLight3.setPosition(new Vector3f(500, 300, -500));
        cornerLight3.setColor(ColorRGBA.White.mult(.25f));
        cornerLight3.setRadius(2000f);
        sceneNode.addLight(cornerLight3); 
        
        PointLight cornerLight4 = new PointLight();
        cornerLight4.setPosition(new Vector3f(500, 300, 500));
        cornerLight4.setColor(ColorRGBA.White.mult(.25f));
        cornerLight4.setRadius(2000f);
        sceneNode.addLight(cornerLight4); 
        
        PointLight sunLight = new PointLight();
        sunLight.setPosition(new Vector3f(0, 500, 0));
        sunLight.setColor(ColorRGBA.White.mult(.25f));
        sunLight.setRadius(2000f);
        sceneNode.addLight(sunLight); 
    }
}
