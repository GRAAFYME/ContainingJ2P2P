package org.protocol;

import javax.vecmath.Point3d;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: Remco
 * Date: 22/11/13
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
public class Container {
    @XmlAttribute
    public String id;

    @XmlElement
    public int arrivalDay;
    @XmlElement
    public int arrivalMonth;
    @XmlElement
    public int arrivalYear;
    @XmlElement
    public String arrivalTill;
    @XmlElement
    public String arrivalFrom;

    @XmlElement
    public int leaveDay;
    @XmlElement
    public int leaveMonth;
    @XmlElement
    public int leaveYear;
    @XmlElement
    public String leaveFrom;
    @XmlElement
    public String leaveTill;
    @XmlElement
    public int arrivalPosX;
    @XmlElement
    public int arrivalPosY;
    @XmlElement
    public int arrivalPosZ;
    @XmlElement
    public String ownerName;
    @XmlElement
    public int nr;
    @XmlElement
    public String arrivalTransportType;
    @XmlElement
    public String leaveTransportType;
    @XmlElement
    public String arrivalCompany;
    @XmlElement
    public String leaveCompany;
    @XmlElement
    public int emptyWeight;
    @XmlElement
    public int fullWeight;
    @XmlElement
    public String contentName;
    @XmlElement
    public String contentType;
    @XmlElement
    public String dangerType;
    @XmlElement(name="ISO")
    public String iso;
    
    public Point3d location = new Point3d(0,0,0);

    public Point3d getLocation()
    {
    	return location;
    }

    public Calendar getArrivalDate()
    {
        Calendar calendar = new GregorianCalendar();

        try {
            Date date = new SimpleDateFormat("hh.mm").parse(arrivalFrom);
            date.setYear(arrivalYear);
            date.setMonth(arrivalMonth);
            date.setDate(arrivalDay);

            calendar.setTime(date);
            System.out.println(calendar.get(Calendar.MONTH));
            System.out.println(calendar.get(Calendar.YEAR));
            System.out.println(calendar.get(Calendar.MINUTE));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }

    public GregorianCalendar getLeaveDate()
    {
        GregorianCalendar calendar = new GregorianCalendar();

        try {
            Date date = new SimpleDateFormat("hh.mm").parse(leaveFrom);
            calendar.setTime(date);
            calendar.set(leaveYear, leaveMonth, leaveDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }


    public Calendar getArrivalBusyTillDate()
    {
        Calendar calendar = new GregorianCalendar();

        try {
            Date date = new SimpleDateFormat("hh.mm").parse(arrivalTill);
            date.setYear(arrivalYear);
            date.setMonth(arrivalMonth);
            date.setDate(arrivalDay);

            calendar.setTime(date);
            System.out.println(calendar.get(Calendar.MONTH));
            System.out.println(calendar.get(Calendar.YEAR));
            System.out.println(calendar.get(Calendar.MINUTE));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }

    public GregorianCalendar getLeaveBusyTillDate()
    {
        GregorianCalendar calendar = new GregorianCalendar();

        try {
            Date date = new SimpleDateFormat("hh.mm").parse(leaveTill);
            calendar.setTime(date);
            calendar.set(leaveYear, leaveMonth, leaveDay);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }
}
