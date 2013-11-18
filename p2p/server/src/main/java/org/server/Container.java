package org.server;

import javafx.geometry.Point3D;

import java.awt.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 18/11/13
 * Time: 11:43
 * To change this template use File | Settings | File Templates.
 */
public class Container {
    public Point3D location;
    public Date arrivalDate;
    public Date leaveDate;
    public String name;
    //Additional attributes go here, not sure they are relevant at all


    public Container(Point3D location, Date arrival, Date leave, String name)
    {
        this.location = location;
        this.arrivalDate = arrival;
        this.leaveDate = leave;
        this.name = name;
    }


}
