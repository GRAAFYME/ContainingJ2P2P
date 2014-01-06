package org.server;

import org.protocol.AGV;
import org.protocol.SeashipCrane;

import java.util.ArrayList;
import java.util.List;

//This class "describes" the basics of the map the client renders
//More importantly, it keeps track of Agvs/cranes states (available/in use)
//algorithms should go in the Simulator class
public class HarborMapState {


    public List<AGV> Agvs;		//List of all Agvs in the harbor
    public List<SeashipCrane> seashipCranes;


    //Returned Agvs are available and thus candidate for new jobs
    public List<AGV> getAvailableAgvs()
    {
        List<AGV> availableAgvs = new ArrayList<AGV>();

        for(AGV a : Agvs)
        {
            if (a.available == true)
            {
                availableAgvs.add(a);
            }
        }

        return availableAgvs;
    }
}
