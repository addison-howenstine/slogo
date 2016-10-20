package slogo_model;

public interface SLOGOModel {
	
	/**
	 * @return - the turtle's X coodrinate from the center of the screen
	 */
	public double xCor();
	
	/**
	 * @return - the turtle's Y coodrinate from the center of the screen
	 */
	public double yCor();
	
	/**
	 * @return - the turtle's heading in degrees
	 */
	public double heading();
	
	/**
	 * @return - the value 1 if pen is down, the value 0 if pen is up
	 */
	public int isPenDown();
	
	/**
	 * @return - the value 1 if turtle is showing, the value 0 if turtle is invisible
	 */
	public int showing();
	
	/**
	 * moves turtle forward in its current heading
	 * 
	 * @param pixels - number of pixels on the screen which to move
	 * @return - value of pixels
	 */
	public double forward(double pixels);

	/**
	 * moves turtle backward in its current heading
	 * 
	 * @param pixels - number of pixels on the screen which to move
	 * @return - value of pixels
	 */
	public double back(double pixels);

	/**
	 * turns turtle counterclockwise
	 * 
	 * @param degrees - angle which the turtle should turn
	 * @return - value of degrees
	 */
	public double left(double degrees);

	/**
	 * turns turtle clockwise
	 * 
	 * @param degrees - angle which the turtle should turn
	 * @return - value of degrees
	 */
	public double right(double degrees);

	/**
	 * turns turtle to an absolute heading
	 * 
	 * @param degrees - value which to turn the turtle to be heading
	 * @return - number of degrees turned
	 */
	public double setHeading(double degrees);

	/**
	 * turns turtle to face the point (x, y), where (0, 0) is the center of the screen
	 * 
	 * @param x
	 * @param y
	 * @return - number of degrees turned
	 */
	public double towards(double x, double y);

	/**
	 * moves turtle to an absolute screen position, where (0, 0) is the center of the screen
	 * 
	 * @param x
	 * @param y
	 * @return - distance turtle moved
	 */
	public double setXY(double x, double y);

	/**
	 * puts pen down such that when the turtle moves, it leaves a trail
	 * 
	 * @return - the value 1
	 */
	public int penDown();

	/**
	 * puts pen up such that when the turtle moves, it does not leave a trail
	 * 
	 * @return - the value 0
	 */
	public int penUp();

	/**
	 * makes turtle visible
	 * 
	 * @return - the value 1
	 */
	public int showTurtle();

	/**
	 * makes turtle invisible
	 * 
	 * @return - the value 0
	 */
	public int hideTurtle();

	/**
	 * moves turtle to the center of the screen (0 0)
	 * 
	 * @return - the distance turtle moved
	 */
	public double home();

	/**
	 * erases turtle's trails and sends it to the home position
	 * 
	 * @return - returns the distance turtle moved
	 */
	public double clearScreen();
	
	/**
	 * @return the mapping for all variables and their corresponding ints
	 */
	public SLOGOVariableMap getVarMap();
	
}
