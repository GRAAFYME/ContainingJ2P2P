/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 22/11/13
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
package org.protocol;

import javax.vecmath.Vector3f;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

public class AGV extends ContainerCarrier
{

    @XmlElement
    public int index; //which Agv to pick

    //XmlTransient means not serialized = ignored
    @XmlTransient
    public Vector3f coordinates;

    @XmlElement
    public List<MotionPathProtocol> routes;

public AGV()  {}

	public AGV(Vector3f coordinates, int index)
	{
        super();

        this.coordinates = coordinates;
        this.index = index;
        routes = new ArrayList<>();
	}
	
	
	
}
