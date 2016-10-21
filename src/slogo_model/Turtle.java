package slogo_model;
import java.util.*;
import instructions.*;

public class Turtle implements SLOGOModel{

	private double xCor;
	private double yCor;
	private double heading;
	private boolean penDown;
	private boolean visible;
	private AbstractMap<String, Double> myVarMap;
	private AbstractMap<String, Instruction> myInstructionMap;

	public Turtle() {
		xCor = 0;
		yCor = 0;
		heading = 0;
		penDown = true;
		visible = true;
		myVarMap = new HashMap<String, Double>();
		myInstructionMap = new HashMap<String, Instruction>();
	}

	@Override
	public double forward(double pixels) {
		// TODO : if pixels is greater than can travel, move to edge, penUP
		// move to opposite edge, put pen down, call recursively forward(pixels - distanceLeftToMove)
		// until distanceLeftToMove = 0
		
		double x = xCor() + pixels * Math.sin(Math.toRadians(heading));
		double y = yCor() + pixels * Math.cos(Math.toRadians(heading));
		setXY(x, y);
		return pixels;
	}

	private double distance(double x0, double x1, double y0, double y1){
		return Math.sqrt( Math.pow( (x1 - x0) , 2 ) +  Math.pow( (y1 - y0) , 2 ));
	}

	@Override
	public double back(double pixels) {
		return (-1) * forward( (-1) * pixels);
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
		return distance(oldX, xCor(), oldY, yCor());
	}

	@Override
	public double penDown() {
		penDown = true;
		return 1;
	}

	@Override
	public double penUp() {
		penDown = false;
		return 0;
	}

	@Override
	public double showTurtle() {
		visible = true;
		return 1;
	}

	@Override
	public double hideTurtle() {
		visible = false;
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
	
	@Override
	public AbstractMap<String, Double> getVarMap(){
		return myVarMap;
	}
	
	@Override
	public AbstractMap<String, Instruction> getInstructionMap(){
		return myInstructionMap;
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