package slogo_view;

import java.util.ResourceBundle;

import javafx.beans.value.ObservableStringValue;
import slogo_controller.SLOGOController;

public interface SLOGOViewExternal {

	/**
	 * Brings up an Alert that informs the user of an error (e.g., bad input)
	 * 
	 * @param errorMessage - the message to be displayed in the Alert dialog box
	 */
	public void showError(String errorMessage);

	/**
	 * Getter for the ResourceBundle for the language currently in use by the user
	 */
	public ResourceBundle getResourceBundle();

	/**
	 * Erases all trails currently showing on the screen
	 */
	public void clearTrails();
	
	public void addUserCommand(String userCommand);
	
	public void addUserVariable(String userVariable);
	
	public SLOGOController getController();
	
	public double getMaxX();
	
	public double getMaxY();
	
	public void setPenColor(String color);
	
	public void setBackgroundColor(String color);

}
