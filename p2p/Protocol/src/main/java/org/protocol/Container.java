package org.protocol;

import javax.vecmath.Point3d;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 22/11/13
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
public class Container {
    @XmlElement
    public String name = "aaa";
    @XmlElement
	public Point3d location = new Point3d(1, 2, 3);

    public Point3d getLocation()
    {
    	return location;
    }
}
