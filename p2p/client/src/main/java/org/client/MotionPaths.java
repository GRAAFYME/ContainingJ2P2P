package org.client;

import java.util.HashMap;

import com.jme3.asset.AssetManager;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.font.BitmapFont;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class MotionPaths {
	
	public MotionPath path, path2, path3, path4, StartingPoint, CrossStartPoint, toSeaCrane, CrossingToTrucks, TrucksLoaded, OtherSidePlatform,
	SeaCraneToOtherSide, OtherSideToAGV,OtherWayAround, CrossingStartToTruck, AGVStartToOtherSide, CrossingToTrains, CrossingToStorageSide1, 
	CrossingToStorageCraneSide1,CrossingToStorageSide2 , CrossingToStorageCraneSide2, CrossingToTruckCrane;
	public MotionEvent motionControl, motionControl2, motionControl3, motionControl4;
	private AssetManager assetManager;
	private BitmapFont guiFont;
	public Node nodeAGV;
	private Node AllAgvNodes;
	float x1 = -470f;
	float z1 = 150f;
	float x2 = -470f;
	float x3 = -445f;
	float x4 = -470f;
	float x5 = -470f;
	float x6 = -470f;
	float z2 = 117;
	HashMap<String, MotionPath> hmMotionPaths = new HashMap<String, MotionPath>();
	
	public MotionPaths( AssetManager am, Node allAgvNodes) {

		this.assetManager = am;
		this.AllAgvNodes = allAgvNodes;
		MotionPath1();
	//	MotionPath2();
	//	MotionPath3();
	//	MotionPath4();
		StartPoint();
		CrossingStart();
		CrossingToSeaCrane();
		CrossingTrucksLoaded();
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
	
	public void StartPoint()
	{
		for (int i = 0; i<50; i++)
		{
		StartingPoint = new MotionPath();
		StartingPoint.addWayPoint(new Vector3f(x1, 260f, z1));
		StartingPoint.addWayPoint(new Vector3f(x1, 260f, z2));
		StartingPoint.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("StartingPoint", StartingPoint);
		x1 += 25;
		}
		
		for (int j = 0; j<50; j++)
		{
			StartingPoint = new MotionPath();
			StartingPoint.addWayPoint(new Vector3f(x5, 260f, 735f));
			StartingPoint.addWayPoint(new Vector3f(x5, 260f, 767f));
			StartingPoint.enableDebugShape(assetManager, AllAgvNodes);
			hmMotionPaths.put("StartingPoint", StartingPoint);
			x5+= 25;
		}
	}
	
	public void CrossingStart()
	{
		
		for (int i = 0; i<31; i++)
		{
		CrossStartPoint = new MotionPath();
		CrossStartPoint.addWayPoint(new Vector3f(-540f, 260f, z2)); //apart MP
		CrossStartPoint.addWayPoint(new Vector3f(x4, 260f, z2));
		CrossStartPoint.addWayPoint(new Vector3f(x3, 260f, z2));
		CrossStartPoint.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("CrossStartPoint", CrossStartPoint);
		x4 += 25;
		}
		
		for (int j = 0; j<50; j++)
		{
		CrossStartPoint = new MotionPath();
		CrossStartPoint.addWayPoint(new Vector3f(-540, 260,767)); //apart MP
		CrossStartPoint.addWayPoint(new Vector3f(x6, 260f, 767f));
		CrossStartPoint.addWayPoint(new Vector3f(x3, 260f, 767f));
		CrossStartPoint.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("CrossStartPoint", CrossStartPoint);
		x6 += 25;
		}
		
	}
	
	public void CrossingStartToTruck()
	{
		for (int i = 0; i<20; i++){
			CrossingStartToTruck = new MotionPath();
			CrossingStartToTruck.addWayPoint(new Vector3f(1,1,1));
			CrossingStartToTruck.enableDebugShape(assetManager, AllAgvNodes);
			hmMotionPaths.put("CrossingStartToTruck", CrossingStartToTruck);
		}
	}
	
	public void CrossingToSeaCrane()
	{		

		toSeaCrane = new MotionPath();
		toSeaCrane.addWayPoint(new Vector3f (-540, 260f, 117));
		toSeaCrane.addWayPoint(new Vector3f (-685, 260f, 117));
		toSeaCrane.addWayPoint(new Vector3f(-685, 260f, 767f));
		toSeaCrane.setCurveTension(0.0f);
		toSeaCrane.enableDebugShape(assetManager, AllAgvNodes);	
		hmMotionPaths.put("ToSeaCrane", toSeaCrane);
	}
	
	public void CrossingTrucksLoaded()
	{
		TrucksLoaded = new MotionPath();
		TrucksLoaded.addWayPoint(new Vector3f (230, 260f, 117));
		TrucksLoaded.addWayPoint(new Vector3f (230, 260f, 25));
		TrucksLoaded.addWayPoint(new Vector3f (-540, 260f, 25));
		TrucksLoaded.addWayPoint(new Vector3f (-540, 260f, 117));
		TrucksLoaded.setCurveTension(0.0f);
		TrucksLoaded.enableDebugShape(assetManager, AllAgvNodes);
		hmMotionPaths.put("TrucksLoaded", TrucksLoaded);
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
			hmMotionPaths.put("CrossingToStorageCraneSide1", CrossingToStorageCraneSide1);
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
				hmMotionPaths.put("CrossingToStorageCraneSide2", CrossingToStorageCraneSide2);
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
			x+= 40;
			}
			}
	
	
	
	
	
	

	public void MotionPath1() {
		path = new MotionPath();
		path.addWayPoint(new Vector3f(778.0f, 260.0f, 152.0f));
		path.addWayPoint(new Vector3f(778.0f, 260.0f, 131.0f));

		path.setCurveTension(0.0f);
		path.enableDebugShape(assetManager, AllAgvNodes);

	}
	
	public void MotionPath2() {
		path2 = new MotionPath();
		path2.addWayPoint(new Vector3f(778.0f, 260.0f, 131.0f));
		path2.addWayPoint(new Vector3f(-685, 260.0f, 131f));
		path2.setCurveTension(0.f);
		path2.enableDebugShape(assetManager, AllAgvNodes);

	}

	public void MotionPath3() {
		path3 = new MotionPath();
		path3.addWayPoint(new Vector3f(-685, 260.0f, 131f));
		path3.addWayPoint(new Vector3f(-685.0f, 260.0f, 700));

		path3.setCurveTension(0.0f);
		path3.enableDebugShape(assetManager, AllAgvNodes);

	}

	public void MotionPath4() {
		path4 = new MotionPath();
		path4.addWayPoint(new Vector3f(1067.0f, 260.0f, 142.0f));
		path4.addWayPoint(new Vector3f(-480.0f, 260.0f, 142.0f));
		path4.addWayPoint(new Vector3f(-480.0f, 260.0f, 740.0f));
		path4.addWayPoint(new Vector3f(1067.0f, 260.0f, 740.0f));
		path4.setCurveTension(0.0f);
		path4.enableDebugShape(assetManager, AllAgvNodes);

	}
}
