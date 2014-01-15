package org.protocol;

import javax.vecmath.Vector3f;
import javax.xml.bind.annotation.XmlElement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Vehicle {
	@XmlElement
	private Vector3f Location;
	@XmlElement
	public List<Container> containers;
	@XmlElement
	public Vector3f location; // truck: only consider values x: 0-19

	public Vehicle() {
	}

	public Vehicle(Vector3f _location, String className) {
		this.Location = _location;
	}

	protected Vector3f GetLocation() {
		return Location;
	}

	protected void SetLocation(Vector3f _location) {
		Location = _location;
	}

	public String getClassName() {
		// Is there a container? yes: just copy its arrival date
		return (containers.size() == 0) ? null
				: containers.get(0).arrivalTransportType;
	}

	public Calendar getArrivalDate() {
		// Is there a container? yes: just copy its arrival date
		return (containers.size() == 0) ? null : containers.get(0)
				.getArrivalDate();
	}

	public GregorianCalendar getLeaveDate() {
		// Is there a container? yes: just copy its arrival date
		return (containers.size() == 0) ? null : containers.get(0)
				.getLeaveDate();
	}

	public Calendar getArrivalBusyTillDate() {
		return (containers.size() == 0) ? null : containers.get(0)
				.getArrivalBusyTillDate();
	}

	public GregorianCalendar getLeaveBusyTillDate() {
		return (containers.size() == 0) ? null : containers.get(0)
				.getLeaveBusyTillDate();
	}
}
