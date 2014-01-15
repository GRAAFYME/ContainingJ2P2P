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
    public List<Vector3f> locationNodeList;
    @XmlElement
    public float length;
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

    public float getLength()
    {
        float totalDistance = 0f;

        for(int i = 0; i < driveUntilWaypoint - 1; i++)
        {
            totalDistance += lengthHelper(locationNodeList.get(i), locationNodeList.get(i + 1));
        }
        return totalDistance;
    }


    private float lengthHelper(Vector3f v1, Vector3f v2)
    {
        Vector3f temp = new Vector3f(v1.x, v1.y, v1.z);
        temp.sub(v2);
        return temp.length();
    }

    public int getNbWayPoints() {
        return locationNodeList.size();
    }


    public String getName()
    {
    	return name;
    }
    
    public Vector3f getWayPoint(int index) {
        return locationNodeList.get(index);
    }
}
