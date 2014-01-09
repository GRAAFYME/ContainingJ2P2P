package org.server;

import org.protocol.*;

import java.util.*;

public class Simulator {
    private long previousTime; //Keep track of time outside since the value is dependant on a speed multiplier
    private PriorityQueue<Vehicle> vehicleQueue;
    private GregorianCalendar currentDate;
    private HarborMapState mapState;

    //Since all containers arrive by some kind of transport vehicle
    // some sorted list of vehicles is the most compact and elegant way to provide the containers (PriorityQueue<Vehicle>)
    public Simulator(PriorityQueue<Vehicle> vehicleQueue)
    {
        this.vehicleQueue = vehicleQueue;
        currentDate = new GregorianCalendar(2004, 12, 13, 0, 10);
        mapState = new HarborMapState();
    }


    boolean debugVar = true;

    //is seconds precise enough? do we want milliseconds? or minutes?
    public ServerProtocol update(int secondsPassed)
    {
        currentDate.add(Calendar.SECOND, secondsPassed);
        mapState.update(currentDate);
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
                    //Grab first truckCrane spot
                    List<TruckCrane> truckCranes = mapState.getAvailableTruckCranes();
                    TruckCrane chosenTruckCrane = truckCranes.get(0);
                    chosenTruckCrane.available = false;
                    vehicleToAdd.location = chosenTruckCrane.Location;
                    mapState.setUnavailable(chosenTruckCrane, vehicleToAdd.getArrivalBusyTillDate());

                    //Agv stuff
                    List<AGV> agvList = mapState.getAvailableAgvs();
                    AGV chosenAgv = agvList.get(0);
                    chosenAgv.routes.add(new MotionPathProtocol("ToSeaCrane", -1));
                    protocol.agvs.add(chosenAgv);
                    mapState.setUnavailable(chosenAgv, vehicleToAdd.getArrivalBusyTillDate());
                    break;
            }


            protocol.vehicles.add(vehicleToAdd);
            return protocol;
        }

        return null;
    }
}



