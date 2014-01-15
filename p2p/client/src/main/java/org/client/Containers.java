package org.client;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Containers extends Node {
	protected Spatial container;
	private String id;

	public Containers(String id, Spatial container) {
		super(id);

		this.id = id;
		this.container = container.clone();
		this.attachChild(this.container);
	}

	public String getId() {
		return this.id;
	}

	public void detachContainer() {
		this.detachChild(this.container);
	}
}