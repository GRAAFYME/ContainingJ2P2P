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
    public String id;
    public Point3d location;    //replace Point3D with "better suited" format?
    public Point3d arrivalDate;
    public Point3d leaveDate;
    public String name;
    public String arriveTimeFrom; //Change these from String to Date or Point
    public String arriveTimeTill; //Change these from String to Date or Point
    public String leaveTimeFrom;  //Change these from String to Date or Point
    public String leaveTimeTill;  //Change these from String to Date or Point
    public String arrivalTransport;//Change these from String to Date or Point;
    public String leaveTransport;
    public String arrivalCompany;
    public String leaveCompany;
    public double emptyWeight;
    public double fullWeight;
    public String contentName;
    public String contentType;
    public String contentDanger;
    public double iso;

    public Container()
    {
    }

    public Container(Point3d location, Point3d arrival, Point3d leave, String name)
    {
        this.location = location;
        this.arrivalDate = arrival;
        this.leaveDate = leave;
        this.name = name;
    }


}
