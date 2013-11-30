package org.client;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

/**
 *
 * @author Melinda
 */
public class FreeMovingCrane {
    
    //AssetManager is very important!!
    AssetManager assetManager;
    
    //For the animation
    boolean animateSlider;
    boolean backForwards;
    
    //Local translation
    float x, y, z;
    
    //Point A & B
    Vector3f hookA1, hookA2, hookB1, hookB2, start, end;
    
    //Three different spatials to create one model
    Spatial zeeKraan;
    Spatial zeeKraanSlider;
    Spatial zeeKraanHook;
    Node containerCrane;
    
    public FreeMovingCrane(AssetManager assetManager, float x, float y, float z)
    {
        this.assetManager = assetManager;
        this.x = x;
        this.y = y;
        this.z = z;
        
        hookA1 = new Vector3f(x,y,z);
        hookA2 = new Vector3f(x,y+9,z);
        hookB1 = new Vector3f(x-25,y+9,z);
        hookB2 = new Vector3f(x-25,y,z);
        start = hookA1;
        end = hookA2;
        
        animateSlider = false;
        backForwards = false;
//        animateHook1 = true; //FOR TESTING, MAIN HAS TO ACTIVATE THE ANIMATIONS WHEN THE CONTAINERS ARE BEING REMOVED FROM THE SHIP/TRAIN/TRUCK!
//        animateSlider1 = false;
//        animateHook2 = false;
//        animateSlider2 = false;
    }
    
    public Node loadModels()
    {
        /** Load a model. Uses model and texture from jme3-test-data library! */ 
        zeeKraan = assetManager.loadModel("Models/crane/zeeKraan.obj");
        Material mat_kraan = new Material( assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture zeeKraan_tex = assetManager.loadTexture("Models/crane/zeekraan.png");
        mat_kraan.setTexture("DiffuseMap", zeeKraan_tex);
        zeeKraan.setMaterial(mat_kraan);
        zeeKraan.setLocalTranslation(x, y, z);
        
        /** Load a model. Uses model and texture from jme3-test-data library! */ 
        zeeKraanSlider = assetManager.loadModel("Models/crane/zeeKraanSlider.obj");
        Material mat_slider = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_slider.setColor("Color", ColorRGBA.Green);
        zeeKraanSlider.setMaterial(mat_slider);
        zeeKraanSlider.setLocalTranslation(x, y, z);
        
        /** Load a model. Uses model and texture from jme3-test-data library! */ 
        zeeKraanHook = assetManager.loadModel("Models/crane/zeeKraanHook.obj");
        Material mat_hook = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_hook.setColor("Color", ColorRGBA.Blue);
        zeeKraanHook.setMaterial(mat_hook);
        zeeKraanHook.setLocalTranslation(x, y+10, z);
        
        containerCrane = new Node();
        containerCrane.attachChild(zeeKraan);
        containerCrane.attachChild(zeeKraanSlider);
        containerCrane.attachChild(zeeKraanHook);
        
        containerCrane.addControl(new RigidBodyControl(0));
        return containerCrane;
    }
    
    public void animation(float speed)
    {
        Vector3f c, posSlider, posHook, velocity;
        float animationSpeed = speed;
        
        posSlider = zeeKraanSlider.getLocalTranslation();
        posHook = zeeKraanHook.getLocalTranslation();
        
        if(!backForwards){
            if(posHook.distance(start) > start.distance(end)){
                if(!animateSlider){
                    if((int)posHook.x == x){
                        start = hookA1;
                        end = hookA2;
                    } else if((int)posHook.x != x){
                        start = hookB1;
                        end = hookB2;
                        if(posHook.distance(start) > start.distance(end)){
                            backForwards = true;
                        }
                    }
                } else{
                    start = hookA2;
                    end = hookB1;
                }
                animateSlider = false;
            }
            if(posSlider.distance(start) < start.distance(end)){
                animateSlider = true;
            }             
        } else{
            if(posHook.distance(start) > start.distance(end)){
                if(!animateSlider){
                    if((int)posHook.x == x){
                        start = hookA2;
                        end = hookA1;
                        if(posHook.distance(start) > start.distance(end)){
                            backForwards = false;
                            //TODO: Set animation stop
                        }
                    } else if((int)posHook.x != x){
                        start = hookB2;
                        end = hookB1;
                    }
                } else{
                    start = hookB1;
                    end = hookA2; 
                }
                animateSlider = false;
            }
            if(posSlider.distance(start) < start.distance(end)){
                animateSlider = true;
            }             
        }
        
        c = end.subtract(start);
        c.normalize();
        velocity = c.multLocal(animationSpeed);
        posHook.addLocal(velocity);
        posSlider.addLocal(velocity);
        
        zeeKraanHook.setLocalTranslation(posHook.x,posHook.y,z);
        if(animateSlider){
            zeeKraanSlider.setLocalTranslation(posSlider.x,y,z);
        }
    }
}
