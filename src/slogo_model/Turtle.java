package slogo_model;
import java.util.*;

/**
 * Concrete implementation of SLOGOModel
 * 
 * @author Addison
 */
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
	public double forward(double pixels){
		return move(pixels, false);
	}
	
	@Override
	public double back(double pixels) {
		return (-1) * move( (-1) * pixels, true);
	}
	
	private double move(double pixels, boolean backwards) {
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
			
			
			boolean prevVisibleState, prevPenState;
			if (Math.abs(xShortestDistance) < Math.abs(yShortestDistance)){
				double modifiedY = yCor() + xShortestDistance * Math.cos(Math.toRadians(heading));
				setXY(boundedX, modifiedY);
				
				prevVisibleState = visible;
				hideTurtle();
				prevPenState = penDown;
				penUp();
				
				setXY(-boundedX, modifiedY);
				maintainWrappingState(prevVisibleState, prevPenState);
				
				return move(pixels - xShortestDistance, backwards);
			}else{
				double modifiedX = xCor() + yShortestDistance * Math.sin(Math.toRadians(heading));
				setXY(modifiedX, boundedY);
				
				prevVisibleState = visible;
				hideTurtle();
				prevPenState = penDown;
				penUp();
				
				setXY(modifiedX, -boundedY);
				maintainWrappingState(prevVisibleState, prevPenState);
				
				return move(pixels - yShortestDistance, backwards);
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
	private int[] getDirection(double newHeading){
		int[] directions = new int[2];
		if (newHeading >= 0){
			//Set right/left
			if (newHeading <= 180){
				directions[0] = 1;
			}else if (newHeading > 180){
				directions[0] = -1;
			}

			//Set up/down
			if (newHeading <= 90 || newHeading >= 270){
				directions[1] = 1;
			}else if (newHeading > 90 && newHeading < 270){
				directions[1] = -1;
			}
		}else if (newHeading < 0){
			if (newHeading <= -180){
				directions[0] = 1;
			}else if (newHeading > -180){
				directions[0] = -1;
			}

			if (newHeading >= -90 || newHeading <= -270){
				directions[1] = 1;
			}else if(newHeading < -90 && newHeading > -270){
				directions[1] = -1;
			}
		}
		return directions;
	}
	
	private void maintainWrappingState(boolean prevVisibleState, boolean prevPenState){
		if (prevVisibleState){
			showTurtle();
		}else{
			hideTurtle();
		}
		if (prevPenState){
			penDown();
		}else{
			penUp();
		}
	}

	private double distance(double x0, double x1, double y0, double y1){
		return Math.sqrt( Math.pow( (x1 - x0) , 2 ) +  Math.pow( (y1 - y0) , 2 ));
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
		double newHeading = orient360(Math.toDegrees(Math.atan( ( x - xCor() ) / ( y - yCor() ) )));
		if( y - yCor() >= 0 )
			setHeading(newHeading);
		else
			setHeading(orient360(newHeading + 180));
		return distanceTurned180(orient360(oldHeading - heading()));
	}

	@Override
	public double setXY(double x, double y) {
		double oldX = xCor();
		double oldY = yCor();
		double oldHeading = heading();
		towards(x,y);
		xCor = x;
		yCor = y;
		heading = oldHeading;
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
		setXY(0,0);
		setHeading(0);
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