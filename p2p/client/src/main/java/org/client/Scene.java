package org.client;

//import com.jme3.app.SimpleApplication;
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
    	//sceneModel = assetManager.loadModel("/Scenes/PlatformScene.j3o");
    	sceneModel = assetManager.loadModel("/Models/platformmodel/platformmodel.j3o");
        sceneShape = CollisionShapeFactory.createMeshShape(sceneModel);
        rbc = new RigidBodyControl(sceneShape, 0);
        sceneNode.attachChild(sceneModel);   //adds the sceneModel to the world
        sceneModel.addControl(rbc); 
        bulletAppState.getPhysicsSpace().add(rbc); 
        initLight();  
    }
    
    //creates and loads the light
    public void initLight(){
    	PointLight sunLight = new PointLight();
        sunLight.setPosition(new Vector3f(650, 2000, 40));
        sunLight.setColor(ColorRGBA.White.mult(1f));
        sunLight.setRadius(3000f);
        sceneNode.addLight(sunLight); 
        
        AmbientLight ambient = new AmbientLight();  //creates a light in the scene, which lights everything from all angles
        ambient.setColor(ColorRGBA.White.mult(5f));
        sceneNode.addLight(ambient);     //adds the light to the world. 
    }
}
