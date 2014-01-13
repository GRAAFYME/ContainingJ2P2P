package org.protocol;

import javax.vecmath.Vector3f;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by Remco on 06/01/14.
 */
public class MotionPathProtocol
{
    @XmlElement
    private List<Vector3f> locationNodeList;
    @XmlElement
    private float length;
    @XmlElement
    public int driveUntilWaypoint;
    @XmlElement
    public String name;

    public MotionPathProtocol() {};
    //use for client->server
    public MotionPathProtocol(List<Vector3f> locationNodeList, float length, String name)
    {
        this.locationNodeList = locationNodeList;
        this.length = length;
        this.name = name;
    }

    //user for server->client, giving Agvs commands
    public MotionPathProtocol(String name, int driveUntilWaypoint)
    {
        this.name = name;
        this.driveUntilWaypoint = driveUntilWaypoint;
    }

    public float getLength() { return length; }

    public int getNbWayPoints() {
        return locationNodeList.size();
    }


    public Vector3f getWayPoint(int index) {
        return locationNodeList.get(index);
    }
}
