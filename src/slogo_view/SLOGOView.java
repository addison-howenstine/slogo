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
	 */
	public ResourceBundle getResourceBundle();

	/**
	 * Erases all trails currently showing on the screen
	 */
	public void clearTrails();
	
	public void addUserCommand(String userCommand);
	
	public void addUserVariable(String userVariable, double value);
	
	public void removeUserVariable(String userVariable);
	
	public SLOGOController getController();
	
	public double getMaxX();
	
	public double getMaxY();
	
	public void setPenColor(int index);
	
	public void setBackgroundColor(int index);
	
	public void setImage(int index);
	
	public void setController(SLOGOController controller);
		
	/**
	 * Called by an Observable model to tell an Observer view that
	 * changes have been made and the screen should update to reflect this
	 */
	public void updateScreen();

	public void setPenWidth(int index);

	public int getPenColor();

	public int getImage();

}
