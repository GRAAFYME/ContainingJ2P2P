package org.client;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class TruckCrane {
AssetManager assetManager;
    
    //Local translation
    float x,y,z;
    
    //Check if crane has to go up or down
    boolean upDown;
    boolean animate, loseContainer;
    int counter;
    
    //Point A & B
    private Vector3f a, b, start, end;
    
    //Two spatials for one model
    Spatial truckKraan;
    Spatial truckKraanHook;
    
    //Node opslag kraan
    Node storageCrane;
    
    public TruckCrane(AssetManager assetManager, float x, float y, float z)
    {
        this.assetManager = assetManager;
        this.x = x;
        this.y = y;
        this.z = z;
        
        a = new Vector3f(x,y,z);
        b = new Vector3f(x,y+9,z);
        start = a;
        end = b;
        
        animate = true;
        loseContainer = false;
    }
    
    public Node loadModels()
    {
    /** Load a model. Uses model and texture from jme3-test-data library! */ 
        truckKraan = assetManager.loadModel("Models/crane/TruckCrane.obj");
        Material mat_kraan = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_kraan.setColor("Color", ColorRGBA.Red);
        truckKraan.setMaterial(mat_kraan);
        truckKraan.setLocalTranslation(x, y, z);
        
        /** Load a model. Uses model and texture from jme3-test-data library! */ 
        truckKraanHook = assetManager.loadModel("Models/crane/TruckCraneHook.obj");
        Material mat_hook = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_hook.setColor("Color", ColorRGBA.Black);
        truckKraanHook.setMaterial(mat_hook);
        truckKraanHook.setLocalTranslation(x, y, z);
           
        storageCrane = new Node();
        storageCrane.attachChild(truckKraan);
        storageCrane.attachChild(truckKraanHook);

        return storageCrane;
    }
    
    public void animation(float speed)
    {
    	//Check if the animation is activated, otherwise DO NOTHING;
    	if(animate){
    		//Create normal vector, position of the hook and the velocity
    		Vector3f c, posHook, velocity;
    		//Speed you want times the time per frame
        
    		//If the TPF is too high, set it lower
    		if(speed > 1.0)
    			speed = 1.0f;
    		float animationSpeed = speed;
        
    		posHook = truckKraanHook.getLocalTranslation();
        
    		if(posHook.distance(start) > a.distance(b)){
    			if(upDown){
    				start = a;
    				end = b;   
    			} else{
    				start = b;
    				end = a;
    			}
    			upDown = !upDown;
    			counter++;
    		}
    		if(counter == 2)
    			loseContainer = true;
    		if(counter == 3)
    			animate = false;
           
    		c = end.subtract(start);
    		c.normalize();      
    		velocity = c.multLocal(animationSpeed * 0.5f);
    		posHook.addLocal(velocity);
        
    		truckKraanHook.setLocalTranslation(posHook);
    	}
    }

}
