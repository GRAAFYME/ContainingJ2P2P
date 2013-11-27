package org.client;

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
    boolean upDown;
    
    //Point A & B
    private Vector3f a, b, start, end;
    
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
        b = new Vector3f(x,y+9,z);
        start = a;
        end = b;
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
        mat_hook.setColor("Color", ColorRGBA.Green);
        opslagKraanHook.setMaterial(mat_hook);
        opslagKraanHook.setLocalTranslation(x, y, z);
           
        storageCrane = new Node();
        storageCrane.attachChild(opslagKraan);
        storageCrane.attachChild(opslagKraanHook);
        return storageCrane;
    }
    
    public void animation(float speed)
    {
        Vector3f c, posHook, velocity;
        //Speed you want times the time per frame
        float animationSpeed = speed;
        
        posHook = opslagKraanHook.getLocalTranslation();
        
        //TODO: Set animation stop (Can do with one boolean)
        
        if(posHook.distance(start) > a.distance(b)){
            if(upDown){
                start = a;
                end = b;   
            } else{
                start = b;
                end = a;
            }
            upDown = !upDown;
        }
        
        c = end.subtract(start);
        c.normalize();      
        velocity = c.multLocal(animationSpeed);
        posHook.addLocal(velocity);
        
        opslagKraanHook.setLocalTranslation(posHook.x, posHook.y, posHook.z);
    }
}
