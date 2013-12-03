package org.client;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class SeaShip {

	AssetManager assetManager;
	Node seaShipNode;
	float x, y, z;
	
    public SeaShip(AssetManager assetManager, float x, float y, float z)
    {
        this.assetManager = assetManager;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Node loadModels()
    {
    /** Load a model. Uses model and texture from jme3-test-data library! */ 
        Spatial seaShip = assetManager.loadModel("Models/Vehicles/seaShip.obj");
        Material mat_ship = new Material( assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_ship.setColor("Color", ColorRGBA.Gray);
        seaShip.setMaterial(mat_ship);
        seaShip.setLocalTranslation(x, y, z);
        
        seaShipNode = new Node();
        seaShipNode.attachChild(seaShip);

        return seaShipNode;
    }
}
