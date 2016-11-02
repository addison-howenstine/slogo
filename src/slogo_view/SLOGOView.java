package slogo_view;

import java.util.ResourceBundle;
import slogo_controller.SLOGOController;
import slogo_model.SLOGOModel;

public interface SLOGOView {

	/**
	 * Brings up an Alert that informs the user of an error (e.g., bad input)
	 * 
	 * @param errorMessage - the message to be displayed in the Alert dialog box
	 */
	public void showError(String errorMessage);

	/**
	 * Getter for the ResourceBundle for the language currently in use by the user
	 * 
	 * @return the ResouceBundle being used by this class
	 */
	public ResourceBundle getResourceBundle();

	/**
	 * Erases all trails currently showing on the screen
	 */
	public void clearTrails();
	
	/**
	 * Adds a user-defined command to the display on the screen
	 * 
	 * @param userCommand - the name of the user-defined command
	 */
	public void addUserCommand(String userCommand);
	
	/**
	 * Adds a user-defined variable to the display on the screen
	 * 
	 * @param userVariable - the name of the variable
	 * @param value - the value of the variable
	 */
	public void addUserVariable(String userVariable, double value);
	
	/**
	 * Removes a user-defined variable from the display on the screen
	 * 
	 * @param userVariable - the name of the variable
	 */
	public void removeUserVariable(String userVariable);
	
	/**
	 * Getter for the SLOGOController being used by the view
	 * 
	 * @return the SLOGOController being used by the view
	 */
	public SLOGOController getController();
	
	/**
	 * Getter for the maximum value of x a turtle can move before it would be off the screen
	 * 
	 * @return the maximum value of x a turtle can move before it would be off the screen
	 */
	public double getMaxX();
	
	/**
	 * Getter for the maximum value of y a turtle can move before it would be off the screen
	 * 
	 * @return the maximum value of y a turtle can move before it would be off the screen
	 */
	public double getMaxY();
	
	/**
	 * Setter for the turtle's pen color
	 * 
	 * @param index - the index of the color to set the turtle's pen to
	 */
	public void setPenColor(int index);
	
	/**
	 * Setter for the background color
	 * 
	 * @param index - the index of the color to set the background to
	 */
	public void setBackgroundColor(int index);
	
	/**
	 * Setter for the image
	 * 
	 * @param index - the index of the image to set the turtle to
	 */
	public void setImage(int index);
	
	/**
	 * Setter for the SLOGOController used by the view
	 * 
	 * @param controller - the controller to be used by the view
	 */
	public void setController(SLOGOController controller);
		
	/**
	 * Called by an Observable model to tell an Observer view that
	 * changes have been made and the screen should update to reflect this
	 */
	public void updateScreen();
	
	/**
	 * Setter for the turtle's pen's width
	 * 
	 * @param index - the width it will set the turtle's pen to
	 */
	public void setPenWidth(int index);
	
	/**
	 * Getter for the color of the pen
	 * 
	 * @return the index of the color of the pen
	 */
	public int getPenColor();
	
	/**
	 * Getter for the image of the turtle
	 * 
	 * @return the index of the image of the turtle
	 */
	public int getImage();
	
	/**
	 * Getter for the size of the turtle's pen
	 * 
	 * @return the size of the turtle's pen
	 */
	public int getPenSize();
	
	/**
	 * Getter for the background color
	 * 
	 * @return the index of the background color
	 */
	public int getBackground();
	
	/**
	 * Getter for the language used by the view
	 * 
	 * @return the String which represents the language used by the view
	 */
	public String getLanguage();
	
	/**
	 * Setter for the language used by the view
	 * 
	 * @param language - the name of the language to be used by the view
	 */
	public void setLanguage(String language);

}
