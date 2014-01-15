package org.client;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class BargeCrane extends Crane {

	public BargeCrane(String id, Vector3f position, Spatial crane,
			Spatial slider, Spatial hook) {
		super(id, position, crane, slider, hook);

		this.crane.rotate(0, -90 * FastMath.DEG_TO_RAD, 0);
		this.slider.rotate(0, -90 * FastMath.DEG_TO_RAD, 0);
		this.hook.rotate(0, -90 * FastMath.DEG_TO_RAD, 0);

		this.crane.scale(2);
		this.slider.scale(2);
		this.hook.scale(2);
	}

}
