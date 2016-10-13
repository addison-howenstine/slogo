package slogo_model;

public interface SLOGOModelExternal {
	
	/**
	 * Accepts a list of commands from the View to be
	 * parsed and handled by the model to control the
	 * turtle's position, heading, pen, visibility, etc.
	 * 
	 * @param command - an input string of commands to be parsed
	 */
	public void run(String command);
	
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
	public int pendown();
	
	/**
	 * @return - the value 1 if turtle is showing, the value 0 if turtle is invisible
	 */
	public int showing();

}
