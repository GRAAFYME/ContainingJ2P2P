package org.server;

import org.protocol.ServerProtocol;
import org.protocol.Vehicle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.PriorityQueue;

public class Simulator {
    private long previousTime; //Keep track of time outside since the value is dependant on a speed multiplier
    private PriorityQueue<Vehicle> vehicleQueue;
    private GregorianCalendar currentDate;

    //Since all containers arrive by some kind of transport vehicle
    // some sorted list of vehicles is the most compact and elegant way to provide the containers (PriorityQueue<Vehicle>)
    public Simulator(PriorityQueue<Vehicle> vehicleQueue)
    {
        this.vehicleQueue = vehicleQueue;
        currentDate = new GregorianCalendar(2004, 12, 13, 0, 10);
    }



    //is seconds precise enough? do we want milliseconds? or minutes?
    public ServerProtocol update(int secondsPassed)
    {
        currentDate.add(Calendar.SECOND, secondsPassed);
        Vehicle vehicle = vehicleQueue.peek();

        //Stil vehicles left in the queue?
        //Is it time to dispatch the vehicle?
        if(vehicle != null && vehicle.getArrivalDate().compareTo(currentDate) >= 0)
        {
            //Create protocol object, which the server object will send to the client
            ServerProtocol protocol = new ServerProtocol();
            protocol.vehicles = new ArrayList<Vehicle>();
            Vehicle vehicleToAdd = vehicleQueue.poll();
            switch(vehicle.getClassName())
            {
                case "vrachtauto":
                    vehicleToAdd.location.x = 1;
                    break;
            }


            protocol.vehicles.add(vehicleToAdd);
            return protocol;
        }

        return null;
    }
}

