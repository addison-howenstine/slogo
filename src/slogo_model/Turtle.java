package slogo_model;
import java.util.*;
import instructions.*;

public class Turtle extends Observable implements SLOGOModel{

	private double xCor;
	private double yCor;
	private double maxX;
	private double maxY;
	private double heading;
	private boolean penDown;
	private boolean visible;

	public Turtle(double maxX, double maxY) {
		this.xCor = 0;
		this.yCor = 0;
		this.maxX = maxX;
		this.maxY = maxY;
		this.heading = 0;
		this.penDown = true;
		this.visible = true;
	}

	@Override
	public double forward(double pixels, boolean backwards) {
		// TODO : if pixels is greater than can travel, move to edge, penUP
		// move to opposite edge, put pen down, call recursively forward(pixels - distanceLeftToMove)
		// until distanceLeftToMove = 0
		double newHeading = (backwards) ? heading + 180 : heading;
		
		int[] directions = getDirection(newHeading);
		double boundedX = maxX * directions[0];
		double boundedY = maxY * directions[1];
		
		double desiredX = xCor() + pixels * Math.sin(Math.toRadians(heading));
		double desiredY = yCor() + pixels * Math.cos(Math.toRadians(heading));
		
		if (Math.abs(desiredX) > Math.abs(boundedX) || Math.abs(desiredY) > Math.abs(boundedY)){
			double distanceToWallX = boundedX - xCor();
			double distanceToWallY = boundedY - yCor();
			
			double yShortestDistance = distanceToWallY / (Math.cos(Math.toRadians(heading)));
			double xShortestDistance = distanceToWallX / (Math.sin(Math.toRadians(heading)));
			
			if (Math.abs(xShortestDistance) < Math.abs(yShortestDistance)){
				double modifiedY = yCor() + xShortestDistance * Math.cos(Math.toRadians(heading));
				setXY(boundedX, modifiedY);
				penUp();
				setXY(-boundedX, modifiedY);
				penDown();
				return forward(pixels - xShortestDistance, backwards);
			}else{
				double modifiedX = xCor() + yShortestDistance * Math.sin(Math.toRadians(heading));
				setXY(modifiedX, boundedY);
				penUp();
				setXY(modifiedX, -boundedY);
				penDown();
				return forward(pixels - yShortestDistance, backwards);
			}
		}
		
		setXY(desiredX, desiredY);
		return pixels;
	}
	
	/**
	 * 
	 * @return int[] directions where directions[0] is an int representing right/left
	 * 								  directions[1] is an int representing up/down
	 */
	private int[] getDirection(double heading){
		int[] directions = new int[2];
		double mod360 = heading % 360;
		if (heading >= 0){
			//Set right/left
			if (mod360 <= 180){
				directions[0] = 1;
			}else if (mod360 > 180){
				directions[0] = -1;
			}
			
			//Set up/down
			if (mod360 <= 90 || mod360 >= 270){
				directions[1] = 1;
			}else if (mod360 > 90 && mod360 < 270){
				directions[1] = -1;
			}
		}else if (heading < 0){
			if (mod360 <= -180){
				directions[0] = 1;
			}else if (mod360 > -180){
				directions[0] = -1;
			}
			
			if (mod360 >= -90 || mod360 <= -270){
				directions[1] = 1;
			}else if(mod360 < -90 && mod360 > -270){
				directions[1] = -1;
			}
		}
		
		return directions;
	}

	private double distance(double x0, double x1, double y0, double y1){
		return Math.sqrt( Math.pow( (x1 - x0) , 2 ) +  Math.pow( (y1 - y0) , 2 ));
	}

	@Override
	public double back(double pixels) {
		return (-1) * forward( (-1) * pixels, true);
	}

	@Override
	public double left(double degrees) {
		setHeading(orient360(heading - degrees));
		return degrees;
	}

	@Override
	public double right(double degrees) {
		return (-1) * left( (-1) * degrees);
	}


	@Override
	public double setHeading(double degrees) {
		double distanceTurned = orient360(heading() - degrees);
		heading = orient360(degrees);
		setChanged();
		notifyObservers();
		return distanceTurned180(distanceTurned);
	}

	@Override
	public double towards(double x, double y) {
		double oldHeading = heading();
		setHeading(orient360(Math.atan( ( x - xCor() ) / ( y - yCor() ) )));
		return distanceTurned180(orient360(oldHeading - heading));
	}

	@Override
	public double setXY(double x, double y) {
		double oldX = xCor();
		double oldY = yCor();
		xCor = x;
		yCor = y;
		setChanged();
		notifyObservers();
		return distance(oldX, xCor(), oldY, yCor());
	}

	@Override
	public double penDown() {
		penDown = true;
		setChanged();
		notifyObservers();
		return 1;
	}

	@Override
	public double penUp() {
		penDown = false;
		setChanged();
		notifyObservers();
		return 0;
	}

	@Override
	public double showTurtle() {
		visible = true;
		setChanged();
		notifyObservers();
		return 1;
	}

	@Override
	public double hideTurtle() {
		visible = false;
		setChanged();
		notifyObservers();
		return 0;
	}

	@Override
	public double home() {
		double oldX = xCor();
		double oldY = yCor();
		setHeading(0);
		setXY(0,0);
		return distance(oldX, xCor(), oldY, yCor());
	}

	@Override
	public double clearScreen() {
		return home();
	}

	@Override
	public double xCor() {
		return xCor;
	}

	@Override
	public double yCor() {
		return yCor;
	}

	@Override
	public double heading() {
		return heading;
	}

	@Override
	public double showing() {
		return (visible) ? 1 : 0;
	}

	@Override
	public double isPenDown() {
		return (penDown) ? 1 : 0;
	}

	/**
	 * @param degree - any number
	 * @return - the equivalent value of degrees between 0 and 360
	 */
	private double orient360(double degree){
		return degree %= 360;
	}
	
	/**
	 * @param distanceTurned - distance turned in degrees between 0 and 360
	 * @return - positive distance turned between 0 and 180
	 */
	private double distanceTurned180(double distanceTurned){
		if (distanceTurned <= 180)
			return distanceTurned;
		else
			return 360 - distanceTurned;
	}
}