package org.protocol;

import javax.vecmath.Point3f;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by Remco on 06/01/14.
 */
public class MotionPathProtocol
{
    @XmlElement
    private List<Point3f> locationNodeList;
    @XmlElement
    private float length;

    public MotionPathProtocol() {};
    public MotionPathProtocol(List<Point3f> locationNodeList, float length)
    {
        this.locationNodeList = locationNodeList;
        this.length = length;
    }

    float getLength() { return length; }

}
