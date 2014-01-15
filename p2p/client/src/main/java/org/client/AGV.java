package org.client;

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

public class AGV extends Node {

	boolean setWireFrame;
	private Spatial AGV;
	private String id;
	Vector3f location;
	String Name;

	// Constructor to create an AGV
	public AGV(String id, Vector3f location, Spatial AGV, String Name) {
		super(id);

		this.Name = Name;
		this.id = id;
		this.AGV = AGV.clone();

		this.scale(10f);
		this.setName(name);

		this.attachChild(this.AGV);
	}

	public String getID() {
		return id;
	}

	public Vector3f getLocation() {
		return location;
	}

	// Attach Container to AGV
	public void attachContainerAGV(Containers cont2) {
		this.attachChild(cont2);
	}
}
