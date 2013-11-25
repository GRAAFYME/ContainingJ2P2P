package org.client;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
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
public class AGV {
	
	public Spatial AGV;
	public Node nodeAGV;
	public AssetManager assetManager;
	public Vector3f location;
	
	public AGV(Vector3f loc, AssetManager am){
		location = loc;
		assetManager = am;
		addAGV(location);
	}
	
    public void addAGV(Vector3f location){
    	AGV = assetManager.loadModel("Models/AGV/AGV.obj" );
    	AGV.setLocalTranslation(location);
    	AGV.scale(10);
    	Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Black);
    	AGV.setMaterial(mat);
    	AGV.setName("Name");
    	nodeAGV = new Node();
    	nodeAGV.attachChild(AGV); // make the AGV appear in the scene  	
    }
}
