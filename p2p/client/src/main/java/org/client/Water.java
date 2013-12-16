/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.client;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.scene.Node;
import com.jme3.texture.Texture2D;
import com.jme3.water.WaterFilter;

/*
 * Authors
 * Joshua Bergsma
 * Remco de Bruin
 * Yme van der Graaf
 * Jeffrey Harders
 * Arjen Pander
 * Melinda de Roo
 * */
public class Water {
    private AssetManager assetManager;    
    private WaterFilter water;
    public Node waterNode;    
    private Vector3f lightDir;
    public FilterPostProcessor fpp;
    public ColorRGBA deepWaterColor;
    
    public Water(AssetManager am, Node node)
    {
        assetManager = am;
        lightDir =  new Vector3f(-4,-1,5);  //coordinates of the specific location of the light.
        waterNode = node;
        deepWaterColor = new ColorRGBA(0.0f, 0.45f, 0.81f, 0.1f);
        initPPcWater();
    }
    //Creates water at a specific level 
    public FilterPostProcessor initPPcWater(){ 
        fpp = new FilterPostProcessor(assetManager); 
        water = new WaterFilter(waterNode, lightDir); 
        water.setCenter(Vector3f.ZERO); 
        water.setRadius(2600); 
        water.setWaveScale(0.003f);   // Height of the waves
        water.setMaxAmplitude(2f); 
        water.setFoamExistence(new Vector3f(1f, -0.5f, 1f)); 
        water.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg")); 
        water.setRefractionStrength(0.2f); 
        water.setWaterHeight(200f);  // Water Height.
        water.setDeepWaterColor(deepWaterColor);
        water.setUseRipples(false);
        fpp.addFilter(water); 
        return fpp;
    }
}
