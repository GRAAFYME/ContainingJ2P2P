package org.client;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

/** Sample 1 - how to get started with the most simple JME 3 application.
 * Display a blue 3D cube and view from all sides by
 * moving the mouse and pressing the WASD keys. */

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
	
	public Node waterNode;

    public static void main(String[] args){
        Client app = new Client();
        app.start(); // start the game
    }

    @Override
    public void simpleInitApp() {
    	testBox();
        waterNode = new Node("Water");
        Water water = new Water(assetManager, waterNode);
        water.initPPcWater();
        viewPort.addProcessor(water.fpp); 
        rootNode.attachChild(waterNode);
    	flyCam.setMoveSpeed(10f);
    	viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
        }
    
    public void testBox(){
    	Box b = new Box(1, 1, 1); // create cube shape
        Geometry geom = new Geometry("Box", b);  // create cube geometry from the shape
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");  // create a simple material
        mat.setColor("Color", ColorRGBA.Blue);   // set color of material to blue
        geom.setMaterial(mat);                   // set the cube's material
        rootNode.attachChild(geom);              // make the cube appear in the scene	
    }

}