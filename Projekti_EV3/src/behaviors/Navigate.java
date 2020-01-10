package behaviors;

import java.util.ArrayList;

import lejos.hardware.Sound;
import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.DestinationUnreachableException;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.SteeringPilot;
import main.ModifiedSteeringPilot;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.ShortestPathFinder;
import lejos.robotics.subsumption.Behavior;
import main.Main;
import main.Motor;

public class Navigate implements Behavior{
	
	private volatile boolean suppressed = false;
	
	//Declare variables for navigation
	Rectangle rectangle = new Rectangle(0,0,100,100);
	Line[] lines = {new Line(0,0,95,0),new Line(0,0,0,95),new Line(0,95,95,95),new Line(95,0,95,95)}; //Vasen seinä
	private  LineMap map= new LineMap(lines,rectangle);
	
	
	private  ArrayList<Waypoint> waypoints =  new ArrayList<Waypoint>();
	private  Pose origin = new Pose(1,1,90);
	private  Path path;
	
	//Parameters for steering pilot are wheel diameter (cm) , drive motor, steering motor, minimun turning radius (cm) , left turn tacho, right turn tacho
	//private ModifiedSteeringPilot pilot = new ModifiedSteeringPilot(4.32,Motor.getMotorRight(),Motor.getSteeringMotor(), 30, 90, 90);
	final private double DIAMETER = 2.9f;
	final private double WIDTH = 16.9f;
	@SuppressWarnings("deprecation")
	private DifferentialPilot pilot = new DifferentialPilot(DIAMETER,WIDTH,Motor.getMotorRight(),Motor.getMotorLeft());
	private Navigator navigator = new Navigator(pilot);
	
	
	
	@Override
	public boolean takeControl() {
		return true;
	}
	@Override
	public void action() {
		while (!suppressed) {
			
			//pilot.calibrateSteering();
			
			navigator.getPoseProvider().setPose(origin);
			
			waypoints.add(new Waypoint(60,50));
			waypoints.add(new Waypoint(75,80));
			waypoints.add(new Waypoint (10,60));
			waypoints.add(new Waypoint (40,10));
			
			Path[] paths = new Path[waypoints.size()];
		    
		    ShortestPathFinder pathfinder = new ShortestPathFinder(map);
		    pathfinder.lengthenLines(7);
			try {
	
			paths[0] = pathfinder.findRoute(navigator.getPoseProvider().getPose(), waypoints.get(0));
				
			for(int i=0;i<paths.length;i++) {
					path = pathfinder.findRoute(navigator.getPoseProvider().getPose(), waypoints.get(i));
					navigator.followPath(path);
					navigator.waitForStop();
					if (i==3) {
						suppress();
						System.exit(0);
					}
				}
		
			} catch (DestinationUnreachableException e) {
				System.out.println("Unreachable");
			}
		}
	}

	@Override
	public void suppress() {
		this.suppressed = true;
		
	}

}
