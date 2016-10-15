package slogo_model;

public class Turtle implements SLOGOModelInternal{
	
	private double xCor;
	private double yCor;
	private double heading;
	private boolean penUp;
	private boolean visible;
	

	public Turtle() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double forward(double pixels) {
		// TODO Auto-generated method stub
		return pixels;
	}

	@Override
	public double back(double pixels) {
		return forward( (-1) * pixels);
	}

	@Override
	public double left(double degrees) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double right(double degrees) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double setHeading(double degrees) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double towards(double x, double y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double setXY(double x, double y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int penDown() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int penUp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int showTurtle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hideTurtle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double home() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double clearScreen() {
		// TODO Auto-generated method stub
		return 0;
	}

}
