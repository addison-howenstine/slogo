package slogo_model;

public class Turtle implements SLOGOModel{

	private double xCor;
	private double yCor;
	private double heading;
	private boolean penDown;
	private boolean visible;
	private SLOGOVariableMap myVarMap;

	public Turtle() {
		xCor = 0;
		yCor = 0;
		heading = 0;
		penDown = true;
		visible = true;
		myVarMap = new SLOGOVariableMap();
	}

	@Override
	public double forward(double pixels) {
		// TODO : if pixels is greater than can travel, move to edge, penUP
		// move to opposite edge, put pen down, call recursively forward(pixels - distanceLeftToMove)
		// until distanceLeftToMove = 0
		
		xCor += pixels * Math.sin(Math.toRadians(heading));
		yCor += pixels * Math.cos(Math.toRadians(heading));
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
		heading = orient360(heading - degrees);
		return degrees;
	}

	@Override
	public double right(double degrees) {
		heading = orient360(heading + degrees);
		return degrees;
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
		heading = orient360(Math.atan( ( x - xCor() ) / ( y - yCor() ) ));
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
	public int penDown() {
		penDown = true;
		return 1;
	}

	@Override
	public int penUp() {
		penDown = false;
		return 0;
	}

	@Override
	public int showTurtle() {
		visible = true;
		return 1;
	}

	@Override
	public int hideTurtle() {
		visible = false;
		return 0;
	}

	@Override
	public double home() {
		double oldX = xCor();
		double oldY = yCor();
		heading = 0;
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
	public int showing() {
		return (visible) ? 1 : 0;
	}

	@Override
	public int isPenDown() {
		return (penDown) ? 1 : 0;
	}
	
	@Override
	public SLOGOVariableMap getVarMap(){
		return myVarMap;
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