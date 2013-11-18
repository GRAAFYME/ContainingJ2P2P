package org.server;

import javax.vecmath.Point3d;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 18/11/13
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class Container {
    public Point3d location;
    public Point3d arrivalDate;
    public Point3d leaveDate;
    public String name;
    //Additional attributes go here, not sure they are relevant at all


    public Container(Point3d location, Point3d arrival, Point3d leave, String name)
    {
        this.location = location;
        this.arrivalDate = arrival;
        this.leaveDate = leave;
        this.name = name;
    }


}
