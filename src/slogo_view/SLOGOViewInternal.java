package slogo_view;

import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public interface SLOGOViewInternal {

	/**
	 * adds Text to the Scene
	 *
	 * @param message - the message that the Text will display
	 * @param x - the x-value at which the Text will be displayed
	 * @param y - the y-value at which the Text will be displayed
	 * @param fontSize - the size of the font of the Text
	 */
	public Text addText(String message, double x, double y, int fontSize);

	/**
	 * adds a TextField to the Scene
	 *
	 * @param message - the message to be displayed in the TextField
	 * @param x - the x-value at which the TextField will be displayed
	 * @param y - the y-value at which the TextField will be displayed
	 * @param handler - the EventHandler that will carry out its handle method when the ENTER 
	 * button is pressed
	 */
	public TextField addTextField(String message, double x, double y);

	/**
	 * adds a Button to the Scene
	 *
	 * @param message - the message to be displayed on the Button
	 * @param x - the x-value at which the Button will be displayed
	 * @param y - the y-value at which the Button will be displayed
	 * @param handler - the EventHandler that will carry out its handle method when the Button is 
	 * pushed
	 */
	public Button addButton(String message, double x, double y, 
			EventHandler<ActionEvent> handler);

	/**
	 * adds a ComboBox to the Scene
	 *
	 * @param x - the x-value at which the ComboBox will be displayed
	 * @param y - the y-value at which the ComboBox will be displayed
	 * @param items - the ObservableList of Strings that the user will choose from when using the 
	 * ComboBox
	 */
	public ComboBox<String> addComboBox(double x, double y, ObservableList<String> items, String defaultValue, 
			ChangeListener<String> listener);

}
