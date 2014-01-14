package org.client;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.font.BitmapFont;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import org.protocol.MotionPathProtocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MotionPaths {
	
	public MotionPath path, path2, path3, path4, StartingPoint, CrossStartPoint, toSeaCrane, CrossingToTrucks, CrossingToBargeCrane, OtherSidePlatform,
	SeaCraneToOtherSide, OtherSideToAGV,OtherWayAround, CrossingStartToTruck, AGVStartToOtherSide, CrossingToTrains, CrossingToStorageSide1, 
	CrossingToStorageCraneSide1,CrossingToStorageSide2 , CrossingToStorageCraneSide2, CrossingToTruckCrane;
	public MotionEvent motionControl, motionControl2, motionControl3, motionControl4;
	private AssetManager assetManager;
	private BitmapFont guiFont;
	public Node nodeAGV;
	private Node AllAgvNodes;


	private HashMap<String, MotionPath> hmMotionPaths = new HashMap<String, MotionPath>();

    public List<javax.vecmath.Vector3f> getWaypointsJavax(MotionPath path)
    {
        List<javax.vecmath.Vector3f> waypointList = new ArrayList<>();

        for(int i = 0; i < path.getNbWayPoints(); i++)
        {
            Vector3f v = path.getWayPoint(i);
            float x = v.x, y = v.y, z = v.z;
            javax.vecmath.Vector3f point = new javax.vecmath.Vector3f(x, y, z);

            waypointList.add(point);
        }

        return waypointList;
    }

    public List<Vector3f> getWaypoints(MotionPath path)
    {
        List<Vector3f> waypointList = new ArrayList<>();

        for(int i = 0; i < path.getNbWayPoints(); i++)
        {
            Vector3f v = path.getWayPoint(i);
            float x = v.x, y = v.y, z = v.z;
            Vector3f point = new Vector3f(x, y, z);

            waypointList.add(point);
        }

        return waypointList;
    }

    public List<MotionPathProtocol> getMotionPathProtocols()
    {
        List<MotionPathProtocol> motionPathProtocolList = new ArrayList<>();


        for(Map.Entry<String, MotionPath> e : hmMotionPaths.entrySet()) {
            MotionPathProtocol newPath = new MotionPathProtocol(getWaypointsJavax(e.getValue()), e.getValue().getLength(), e.getKey());

            motionPathProtocolList.add(newPath);
        }

        return motionPathProtocolList;
    }

    public MotionPath[] getMotionPaths()
    {
        return (MotionPath[])hmMotionPaths.values().toArray();
    }
	
	public MotionPaths( AssetManager am, Node allAgvNodes) {

		this.assetManager = am;
		this.AllAgvNodes = allAgvNodes;
	//	MotionPath1();
	//	MotionPath2();
	//	MotionPath3();
	//	MotionPath4();
		StartPoint();
		CrossingStart();
		CrossingToSeaCrane();
		CrossingToBargeCrane();
		CrossingtoOtherSidePlatform();
		CrossingSeaCraneToCrossingOtherSide();
		CrossingOtherSideToAGVs();
		CrossingStartToOtherWayAround();
		CrossingAGVStartToOtherSide();
		CrossingToTrains();
		CrossingToStorageSide1();
		CrossingToStorageCraneSide1();
		CrossingToStorageSide2();
		CrossingToStorageCraneSide2();
		CrossingToTrucks();
		CrossingToTruckCrane();
		
	}
	public MotionPath getMotionPath(String name)
	 {  
	  MotionPath path = hmMotionPaths.get(name);
	  return path;

	 }
	
	
	public void StartPoint()
	{	float x1 = -470f;
		for (int i = 0; i<50; i++)
		{
		StartingPoint = new MotionPath();
		StartingPoint.addWayPoint(new Vector3f(x1, 260f, 150));
		StartingPoint.addWayPoint(new Vector3f(x1, 260f, 117));
		StartingPoint.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("StartingPoint"+String.valueOf(i) , StartingPoint);
		x1 += 25;
		}
		float x5 = -470f;
		for (int j = 0; j<50; j++)
		{
			StartingPoint = new MotionPath();
			StartingPoint.addWayPoint(new Vector3f(x5, 260f, 735f));
			StartingPoint.addWayPoint(new Vector3f(x5, 260f, 767f));
			StartingPoint.enableDebugShape(assetManager, AllAgvNodes);
			hmMotionPaths.put("StartingPoint"+String.valueOf(j), StartingPoint);
			x5+= 25;
		}
	}
	
	public void CrossingStart()
	{
		float x4 = -470f;
		for (int i = 0; i<31; i++)
		{
		CrossStartPoint = new MotionPath();
		CrossStartPoint.addWayPoint(new Vector3f(-540f, 260f, 117));
		CrossStartPoint.addWayPoint(new Vector3f(x4, 260f, 117));
		CrossStartPoint.addWayPoint(new Vector3f(-445, 260f, 117));
		CrossStartPoint.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("CrossStartPoint"+String.valueOf(i), CrossStartPoint);
		x4 += 25;
		}
		float x6 = -470f;
		for (int j = 0; j<50; j++)
		{
		CrossStartPoint = new MotionPath();
		CrossStartPoint.addWayPoint(new Vector3f(-540, 260,767));
		CrossStartPoint.addWayPoint(new Vector3f(x6, 260f, 767f));
		CrossStartPoint.addWayPoint(new Vector3f(-445, 260f, 767f));
		CrossStartPoint.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("CrossStartPoint"+String.valueOf(j), CrossStartPoint);
		x6 += 25;
		}
		
	}
	
	public void CrossingStartToTruck()
	{
		for (int i = 0; i<20; i++){
			CrossingStartToTruck = new MotionPath();
			CrossingStartToTruck.addWayPoint(new Vector3f(1,1,1));
			CrossingStartToTruck.enableDebugShape(assetManager, AllAgvNodes);
			hmMotionPaths.put("CrossingStartToTruck"+String.valueOf(i), CrossingStartToTruck);
		}
	}
	
	public void CrossingToSeaCrane()
	{		

		toSeaCrane = new MotionPath();
		toSeaCrane.addWayPoint(new Vector3f (-540, 260f, 117));
		toSeaCrane.addWayPoint(new Vector3f (-685, 260f, 117));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260, 200));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260, 250));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260, 300));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260, 350));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260, 400));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260, 450));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260, 500));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260, 550));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260, 600));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260, 650));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260f, 767f));
		toSeaCrane.setCurveTension(0.0f);
		toSeaCrane.enableDebugShape(assetManager, AllAgvNodes);	
		hmMotionPaths.put("ToSeaCrane", toSeaCrane);
	}
	
	public void CrossingToBargeCrane()
	{
		CrossingToBargeCrane = new MotionPath();
		CrossingToBargeCrane.addWayPoint(new Vector3f (230, 260f, 117));
		CrossingToBargeCrane.addWayPoint(new Vector3f (230, 260f, 25));
		CrossingToBargeCrane.addWayPoint(new Vector3f (200, 260, 25));
		CrossingToBargeCrane.addWayPoint(new Vector3f (100, 260, 25));
		CrossingToBargeCrane.addWayPoint(new Vector3f (0, 260, 25));
		CrossingToBargeCrane.addWayPoint(new Vector3f (-100, 260, 25));
		CrossingToBargeCrane.addWayPoint(new Vector3f (-200, 260, 25));
		CrossingToBargeCrane.addWayPoint(new Vector3f (-300, 260, 25));
		CrossingToBargeCrane.addWayPoint(new Vector3f (-400, 260, 25));
		CrossingToBargeCrane.addWayPoint(new Vector3f (-500, 260, 25));
		CrossingToBargeCrane.addWayPoint(new Vector3f (-540, 260f, 25));
		CrossingToBargeCrane.addWayPoint(new Vector3f (-540, 260f, 117));
		CrossingToBargeCrane.setCurveTension(0.0f);
		CrossingToBargeCrane.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("TrucksLoaded", CrossingToBargeCrane);
	}
	
	public void CrossingtoOtherSidePlatform()
	{
		OtherSidePlatform = new MotionPath();
		OtherSidePlatform.addWayPoint(new Vector3f (-540, 260f, 117));
		OtherSidePlatform.addWayPoint(new Vector3f (-540, 260f, 170));
		OtherSidePlatform.addWayPoint(new Vector3f (-540, 260, 721));
		OtherSidePlatform.addWayPoint(new Vector3f (-540, 260f, 767));
		OtherSidePlatform.setCurveTension(0.0f);
		OtherSidePlatform.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("OtherSidePlatform", OtherSidePlatform);
		
	}
	
	public void CrossingSeaCraneToCrossingOtherSide()
	{
		SeaCraneToOtherSide = new MotionPath();
		SeaCraneToOtherSide.addWayPoint(new Vector3f (-685, 260f, 767));
		SeaCraneToOtherSide.addWayPoint(new Vector3f (-540, 260f, 767));
		SeaCraneToOtherSide.setCurveTension(0.0f);
		SeaCraneToOtherSide.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("SeaCraneToOtherSide", SeaCraneToOtherSide);
	}
	
	public void CrossingOtherSideToAGVs()
	{
		OtherSideToAGV = new MotionPath();
		OtherSideToAGV.addWayPoint(new Vector3f (-540, 260f, 767));
		OtherSideToAGV.addWayPoint(new Vector3f (-540, 260f, 767));
		OtherSideToAGV.setCurveTension(0.0f);
		OtherSideToAGV.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("OtherSideToAGV", OtherSideToAGV);
	}
	
	public void CrossingStartToOtherWayAround()
	{
		OtherWayAround = new MotionPath();
		OtherWayAround.addWayPoint(new Vector3f (1050, 260f, 117));
		OtherWayAround.addWayPoint(new Vector3f (1085, 260f, 117));
		OtherWayAround.addWayPoint(new Vector3f (1085, 260f, 170));
		OtherWayAround.addWayPoint(new Vector3f(1085, 260f, 721));
		OtherWayAround.addWayPoint(new Vector3f (1085, 260f, 767));
		OtherWayAround.setCurveTension(0.0f);
		OtherWayAround.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("OtherWayAround", OtherWayAround);
	}
	
	public void CrossingAGVStartToOtherSide()
	{
		AGVStartToOtherSide = new MotionPath();
		AGVStartToOtherSide.addWayPoint(new Vector3f(1085, 260, 767));
		AGVStartToOtherSide.addWayPoint(new Vector3f(755, 260, 767));
		AGVStartToOtherSide.setCurveTension(0);
		AGVStartToOtherSide.enableDebugShape (assetManager, AllAgvNodes);
		hmMotionPaths.put("AGVStartToOtherSide", AGVStartToOtherSide);
	}
	
	public void CrossingToTrains()
	{
		CrossingToTrains = new MotionPath();
		CrossingToTrains.addWayPoint(new Vector3f(-540, 260, 767));
		CrossingToTrains.addWayPoint (new Vector3f(-540, 260, 786));
		CrossingToTrains.addWayPoint(new Vector3f(-100, 260, 786));
		CrossingToTrains.addWayPoint(new Vector3f(150, 260, 786));
		CrossingToTrains.addWayPoint(new Vector3f(400, 260, 786));
		CrossingToTrains.addWayPoint(new Vector3f(650, 260, 786));
		CrossingToTrains.addWayPoint(new Vector3f(1085, 260, 786));
		CrossingToTrains.addWayPoint(new Vector3f(1085, 260, 767));
		CrossingToTrains.setCurveTension(0);
		CrossingToTrains.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("CrossingToTrains", CrossingToTrains);
	}
	
	public void CrossingToStorageSide1()
	{
		CrossingToStorageSide1 = new MotionPath();
		CrossingToStorageSide1.addWayPoint(new Vector3f (-540, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (-460, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (-400, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (-340, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (-280, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (-220, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (-160, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (-100, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (-40, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (20, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (80, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (140, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (200, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (260, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (320, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (380, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (440, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (500, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (560, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (620, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (680, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (740, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (800, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (860, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (920, 260, 721));
		CrossingToStorageSide1.addWayPoint(new Vector3f (1085, 260, 721));
		CrossingToStorageSide1.setCurveTension(0);
		CrossingToStorageSide1.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("CrossingToStorageSide1", CrossingToStorageSide1);
		}	
	
		public void CrossingToStorageCraneSide1()
		{
			float x = -460;
			for (int i = 0; i<24; i++)
			{
			CrossingToStorageCraneSide1 = new MotionPath();
			CrossingToStorageCraneSide1.addWayPoint(new Vector3f(x, 260, 721));
			CrossingToStorageCraneSide1.addWayPoint(new Vector3f(x, 260, 667));
			CrossingToStorageCraneSide1.setCurveTension(0);
			CrossingToStorageCraneSide1.enableDebugShape(assetManager, AllAgvNodes);
			hmMotionPaths.put("CrossingToStorageCraneSide1"+String.valueOf(i), CrossingToStorageCraneSide1);
			x+= 60;
			}
		}

		public void CrossingToStorageSide2()
		{
			CrossingToStorageSide2 = new MotionPath();			
			CrossingToStorageSide2.addWayPoint(new Vector3f (-540, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (-460, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (-400, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (-340, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (-280, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (-220, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (-160, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (-100, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (-40, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (20, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (80, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (140, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (200, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (260, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (320, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (380, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (440, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (500, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (560, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (620, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (680, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (740, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (800, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (860, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (920, 260, 170));
			CrossingToStorageSide2.addWayPoint(new Vector3f (1085, 260, 170));
			CrossingToStorageSide2.setCurveTension(0);
			CrossingToStorageSide2.enableDebugShape(assetManager, AllAgvNodes);
			hmMotionPaths.put("CrossingToStorageSide2", CrossingToStorageSide2);
			}
		
		
			public void CrossingToStorageCraneSide2()
			{
				float x = -460;
				for (int i = 0; i<24; i++)
				{
				CrossingToStorageCraneSide2 = new MotionPath();
				CrossingToStorageCraneSide2.addWayPoint(new Vector3f(x, 260, 170));
				CrossingToStorageCraneSide2.addWayPoint(new Vector3f(x, 260, 224));
				CrossingToStorageCraneSide2.setCurveTension(0);
				CrossingToStorageCraneSide2.enableDebugShape(assetManager, AllAgvNodes);
				hmMotionPaths.put("CrossingToStorageCraneSide2"+String.valueOf(i), CrossingToStorageCraneSide2);
				x+= 60;
				}
			}
			
			public void CrossingToTrucks()
			{
				CrossingToTrucks = new MotionPath();
				CrossingToTrucks.addWayPoint(new Vector3f(280, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(290, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(305, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(330, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(355, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(370, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(380, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(405, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(410, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(430, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(450, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(455, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(480, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(490, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(505, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(530, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(555, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(570, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(580, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(605, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(610, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(630, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(650, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(655, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(680, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(690, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(705, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(730, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(755, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(770, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(810, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(850, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(890, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(930, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(970, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(1010, 260, 117));
				CrossingToTrucks.addWayPoint(new Vector3f(1050, 260, 117));
				CrossingToTrucks.setCurveTension(0);
				CrossingToTrucks.enableDebugShape(assetManager, AllAgvNodes);
				hmMotionPaths.put("CrossingToTrucks", CrossingToStorageCraneSide2);
			}
			
			public void CrossingToTruckCrane()
			{
			float x= 290;
			for (int i = 0; i<20; i++)
			{
			CrossingToTruckCrane = new MotionPath();
			CrossingToTruckCrane.addWayPoint(new Vector3f(x, 260, 117));
			CrossingToTruckCrane.addWayPoint(new Vector3f(x, 260, 90));
			CrossingToTruckCrane.setCurveTension(0);
			CrossingToTruckCrane.enableDebugShape(assetManager, AllAgvNodes);
			hmMotionPaths.put("CrossingToTruckCranes"+String.valueOf(i), CrossingToStorageCraneSide2);
			x+= 40;
			}
			}
}
