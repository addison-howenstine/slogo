package slogo_model;

public class Turtle implements SLOGOModel{

	private double xCor;
	private double yCor;
	private double heading;
	private boolean penDown;
	private boolean visible;


	public Turtle() {
		xCor = 0;
		yCor = 0;
		heading = 0;
		penDown = true;
		visible = true;
	}

	@Override
	public double forward(double pixels) {
		double x0 = xCor;
		double y0 = yCor;
		xCor += pixels * Math.sin(Math.toRadians(heading));
		yCor += pixels * Math.cos(Math.toRadians(heading));
		return distance(x0, xCor, y0, yCor);
	}

	private double distance(double x0, double x1, double y0, double y1){
		return Math.sqrt( Math.pow( (x1 - x0) , 2 ) +  Math.pow( (y1 - y0) , 2 ));
	}

	@Override
	public double back(double pixels) {
		return forward( (-1) * pixels);
	}

	@Override
	public double left(double degrees) {
		return right ( (-1) * degrees);
	}

	@Override
	public double right(double degrees) {
		heading += degrees;
		return degrees;
	}

	@Override
	public double setHeading(double degrees) {
		double d0 = heading;
		heading = degrees;
		return d0 - heading;
	}

	@Override
	public double towards(double x, double y) {
		double oldHeading = heading();
		heading = Math.atan( ( x - xCor() ) / ( y - yCor() ) );
		return (oldHeading - heading) % 360;
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
		if (visible)
			return 1;
		else
			return 0;
	}

	@Override
	public int isPenDown() {
		if (penDown)
			return 1;
		else
			return 0;
	}
}