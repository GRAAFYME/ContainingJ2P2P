package org.server;

import org.protocol.*;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//This class "describes" the basics of the map the client renders
//More importantly, it keeps track of Agvs/cranes states (available/in use)
//algorithms should go in the Simulator class
public class HarborMapState {
	public List<ContainerCarrier> unavailableList;

	public List<AGV> AgvList; // List of all Agvs in the harbor
	public List<TruckCrane> truckCraneList;
	public List<SeashipCrane> seashipCraneList;
	public List<MotionPathProtocol> routeList;

	public HarborMapState() {
		initiate();
	}

	public void initiate() {
		// reset the lists
		AgvList = new ArrayList<AGV>();
		seashipCraneList = new ArrayList<>();
		truckCraneList = new ArrayList<>();
		unavailableList = new ArrayList<>();
		routeList = new ArrayList<>();

		// recreate the hardcoded coordinates in the client

		// truckCranes
		for (int i = 1; i <= 20; i++) {
			Vector3f location = new Vector3f(250 + (i * 40), 255, 90);
			truckCraneList.add(new TruckCrane(location, i));
		}

		// AGVs
		// TODO: TEST THESE LOCATIONS!
		for (int i = 0; i < 50; i++) {
			float x1 = -470f;
			float z1 = 150f;
			Vector3f location = new Vector3f(x1 + i * 25, 260, z1);
			AgvList.add(new AGV(location, AgvList.size()));
		}
		for (int j = 0; j < 50; j++) {
			float x2 = -470f;
			float z2 = 735f;
			Vector3f location = new Vector3f(x2 + j * 25, 260, z2);
			AgvList.add(new AGV(location, AgvList.size()));
		}

	}

	public void update(Calendar currentDate) {
		// Check if something is available again
		for (ContainerCarrier c : unavailableList) {
			if (currentDate.compareTo(c.availableAgainDate) >= 0) {
				c.available = true;
				c.availableAgainDate = null;
				unavailableList.remove(c);
			}
		}
	}

	public void setUnavailable(ContainerCarrier carrier, Calendar date) {
		carrier.available = false;
		carrier.availableAgainDate = date;
		unavailableList.add(carrier);
	}

	// java wtf
	private List<ContainerCarrier> getAvailableList(
			List<? extends ContainerCarrier> carrierList) {
		List<ContainerCarrier> availableList = new ArrayList<ContainerCarrier>();
		for (ContainerCarrier c : carrierList) {
			if (c.available == true) {
				availableList.add(c);
			}
		}
		return availableList;
	}

	// Returned Agvs are available and thus candidate for new jobs
	public List<AGV> getAvailableAgvs() {
		List<AGV> availableList = new ArrayList<>();
		for (AGV c : AgvList) {
			if (c.available == true) {
				availableList.add(c);
			}
		}
		return availableList;
	}

	// Returned Agvs are available and thus candidate for new jobs
	public List<TruckCrane> getAvailableTruckCranes() {
		List<TruckCrane> availableList = new ArrayList<>();
		for (TruckCrane c : truckCraneList) {
			if (c.available == true) {
				availableList.add(c);
			}
		}
		return availableList;
	}

	public void setRoutes(List<MotionPathProtocol> routes) {
		this.routeList = routes;
	}
}
