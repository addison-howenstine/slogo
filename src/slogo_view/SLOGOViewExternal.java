package slogo_view;

import java.util.ResourceBundle;
import slogo_controller.SLOGOController;
import slogo_model.SLOGOModel;

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
	
	public void setController(SLOGOController controller);
	
	/**
	 * @param model - new SLOGOModel to which the view will listen for changes
	 */
	public void addModel(SLOGOModel model);
	
	/**
	 * Called by an Observable model to tell an Observer view that
	 * changes have been made and the screen should update to reflect this
	 */
	public void updateScreen();

}
