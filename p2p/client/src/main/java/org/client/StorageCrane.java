package org.client;

import java.lang.reflect.Array;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Melinda
 */
public class StorageCrane {
    
    AssetManager assetManager;
    
    //Local translation
    float x,y,z;
    
    //Check if crane has to go up or down
    boolean upDown, frontback;
    boolean store, animate, loseContainer;
    int counter;
    
    //Point A & B
    private Vector3f a, b, d, e, startHook, endHook, startCrane, endCrane;
    
    //Two spatials for one model
    Spatial opslagKraan;
    Spatial opslagKraanHook;
    
    //Node opslag kraan
    Node storageCrane;
    
    public StorageCrane(AssetManager assetManager, float x, float y, float z)
    {
        this.assetManager = assetManager;
        this.x = x;
        this.y = y;
        this.z = z;
        
        a = new Vector3f(x,y,z);
        b = new Vector3f(x,y+13,z);
        startHook = a;
        endHook = b;
        
        d = new Vector3f(x,y,z);
        e = new Vector3f(x+2.5f,y,z);
        startCrane = d;
        endCrane = e;
        
        animate = true;
        store = true;
        loseContainer = false;
    }
    
    public Node loadModels()
    {
    /** Load a model. Uses model and texture from jme3-test-data library! */ 
        opslagKraan = assetManager.loadModel("Models/crane/storageCrane.obj");
        Material mat_kraan = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_kraan.setColor("Color", ColorRGBA.Red);
        opslagKraan.setMaterial(mat_kraan);
        opslagKraan.setLocalTranslation(x, y, z);
        
        /** Load a model. Uses model and texture from jme3-test-data library! */ 
        opslagKraanHook = assetManager.loadModel("Models/crane/storageCraneHook.obj");
        Material mat_hook = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_hook.setColor("Color", ColorRGBA.Black);
        opslagKraanHook.setMaterial(mat_hook);
        opslagKraanHook.setLocalTranslation(x, y+13, z);
           
        storageCrane = new Node();
        storageCrane.attachChild(opslagKraan);
        storageCrane.attachChild(opslagKraanHook);

        return storageCrane;
    }
    
    public void animation(double speed)
    {
    	//Check if the animation is activated, otherwise DO NOTHING;
    	if(animate)
    	{
			//Create normal vector, position of the hook and the velocity
			Vector3f c, posHook, velocity;
			//Speed you want times the time per frame

			//If the TPF is too high, set it lower
			if(speed > 1 || speed == 0)
				speed = 0.01;
			double animationSpeed = speed * 0.5;

			posHook = opslagKraanHook.getLocalTranslation();
			
    		if(posHook.distance(startHook) > a.distance(b))
    		{
    			if(upDown)
    			{
    				startHook = a;
    				endHook = b;
    			} 
    			else
    			{
    				startHook = b;
    				endHook = a;
    			}
    			upDown = !upDown;
    			counter++;
    		}
    		if(counter == 2)
    			loseContainer = true;
    		if(counter == 3)
    			animate = false;
    	
      		c = endHook.subtract(startHook);
     		c.normalize();      
     		velocity = c.multLocal((float)animationSpeed);
     		posHook.addLocal(velocity);
         
     		opslagKraanHook.setLocalTranslation(posHook);
    	}
    }
    
    public void move(double speed, int row, int floor)
    {
    	if(!animate && counter > 0)
    	{
    		Vector3f f, posHook, posCrane, velocity;
    		e = new Vector3f(x+(row*13f),y,z);
    		
    		//If the TPF is too high, set it lower
    		if(speed > 1 || speed == 0)
    			speed = 0.01;
    		speed = speed * 0.5;
    		
    		posCrane = opslagKraan.getLocalTranslation();
    		posHook = opslagKraanHook.getLocalTranslation();
    		
    		if(posCrane.distance(startCrane) > d.distance(e))
    		{
    			if(frontback)
    			{
    				startCrane = d;
    				endCrane = e;
    			}
    			else
    			{
    				startCrane = e;
    				endCrane = d;
    			}
    			frontback = !frontback;
    			animate = true;
    			
    			if(counter < 6)
    			{
    				counter++;
    				upDown = false;
    				a = new Vector3f(posHook.x, y+(floor*2.5f), z);
    				b = new Vector3f(posHook.x, y+13,z);
    			}
    			else
    			{
    				counter = 0;
    				a = new Vector3f(x, y, z);
    			}
    		}
    		
    		f = endCrane.subtract(startCrane);
    		f.normalize();
    		velocity = f.multLocal((float)speed);
    		posCrane.addLocal(velocity);
    		posHook.addLocal(velocity);
    		
    		opslagKraan.setLocalTranslation(posCrane);
    		opslagKraanHook.setLocalTranslation(posHook.x,opslagKraanHook.getLocalTranslation().y, 
    											opslagKraanHook.getLocalTranslation().z);
    	}
    }
    
    public void setStore(double speed, int row, int floor)
    {
    	if(counter < 7)
    	{
    		System.out.println("Counter: " + counter);
    		animation(speed);
    		move(speed, row, floor);
    		setStore(speed, row, floor);
    	}
    }
    
    public void setToVehicle(double speed, int row, int floor)
    {
    	if(counter < 7)
    	{
    		System.out.println("Counter: " + counter);
    		animation(speed);
    		move(speed, row, floor);
    		setToVehicle(speed, row, floor);
    	}
    }
    
    public void setValues()
    {
    	if(store)
    	{
    		animate = true;
    	}
    	else
    	{
    		animate = false;
    	}
    }
}
