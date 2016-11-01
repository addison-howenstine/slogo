package slogo_view;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SLOGOScreenDisplays {
	private static final int DISPLAY_AREA_X = 815;
	private static final int HISTORY_Y = 80;
	private static final int DISPLAY_WIDTH = 450;
	private static final int DISPLAY_HEIGHT = 188;
	private static final double COMMAND_DISPLAY_Y = DISPLAY_HEIGHT + 87.5;
	private static final int VARIABLE_DISPLAY_Y = 471;
	
	
	public VBox myUserDefinedCommandsDisplay;
	public VBox myCommandHistoryDisplay;
	public ArrayList<Button> myVariableButtons = new ArrayList<Button>();
	public VBox myVariableDisplay;
	private SLOGOScreen myScreen;

	public SLOGOScreenDisplays(SLOGOScreen screen) {
		myScreen = screen;
	}
	
	protected void setUpDisplays(){
		setUpCommandHistoryDisplay();
		setUpUserDefinedCommandsDisplay();
		setUpVariableDisplay();
	}
	
	private void setUpCommandHistoryDisplay() {
		myCommandHistoryDisplay = myScreen.getBuilder().addScrollableVBox(DISPLAY_AREA_X, HISTORY_Y, DISPLAY_WIDTH, 
				DISPLAY_HEIGHT);
		myScreen.getPlayground().getHistory().addListener(new ListChangeListener<Object>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void onChanged(ListChangeListener.Change change) {
				while (change.next()) {
					List<String> list = change.getAddedSubList();
					for (String s : list) {
						configureButtonToRunCommand(s, "command-history-button", myCommandHistoryDisplay);
					}
				}
			}
		});
	}
	
	private void setUpUserDefinedCommandsDisplay() {
		myUserDefinedCommandsDisplay = myScreen.getBuilder().addScrollableVBox(DISPLAY_AREA_X, COMMAND_DISPLAY_Y,
				DISPLAY_WIDTH, DISPLAY_HEIGHT);
		myScreen.getPlayground().getUserCommands().addListener(new ListChangeListener<Object>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void onChanged(ListChangeListener.Change change) {
				while (change.next()) {
					List<String> list = change.getAddedSubList();
					for (String s : list) {
						if (myScreen.getPlayground().getUserCommands().indexOf(s) == myScreen.getPlayground().getUserCommands().lastIndexOf(s)) {
							myScreen.getPlayground().getUserCommands().remove(myScreen.getPlayground().getUserCommands().indexOf(s));
							removeButtonDuplicates(s);
						}
						configureButtonToRunCommand(s, "user-defined-button", myUserDefinedCommandsDisplay);
					}
				}
			}
		});
	}
	
	private void removeButtonDuplicates(String s) {
		for (int i = 0; i < myUserDefinedCommandsDisplay.getChildren().size(); i++) {
			Button button = (Button) (myUserDefinedCommandsDisplay.getChildren().get(i));
			if (button.getText().equals(s)) {
				myUserDefinedCommandsDisplay.getChildren().remove(i);
				i--;
			}
		}
	}
	
	private void setUpVariableDisplay(){
		myVariableDisplay = myScreen.getBuilder().addScrollableVBox(DISPLAY_AREA_X, VARIABLE_DISPLAY_Y, DISPLAY_WIDTH, 
				DISPLAY_HEIGHT);
		myScreen.getPlayground().getUserVariables().addListener(new ListChangeListener<Object>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void onChanged(ListChangeListener.Change change){
				while (change.next()){
					List<String> list = change.getAddedSubList();
					removeVariableFromDisplay(myVariableDisplay);
					for (String s : list) {
						Button button = addButtonToDisplay(s, "command-history-button", myVariableDisplay);
						myVariableButtons.add(button);
					}
				}
			}
		});
	}
	
	protected void removeVariableFromDisplay(VBox variableDisplay) {
		for (String oldVariable: myScreen.getPlayground().getOldVariables()){
			for (Button currButton: myVariableButtons){
				if (currButton.getText().equals(oldVariable)){
					variableDisplay.getChildren().remove(currButton);
					myVariableButtons.remove(currButton);
					myScreen.getPlayground().getOldVariables().remove(oldVariable);
					return;
				}
			}
		}
	}

	private void configureButtonToRunCommand(String text, String id, VBox display) {
		Button command = addButtonToDisplay(text, id, display);
		command.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				myScreen.getPlayground().getController().run(text);
			}
		});
	}

	private Button addButtonToDisplay(String text, String id, VBox display) {
		Button command = new Button(text);
		command.setId(id);
		command.setMinWidth(display.getMinWidth());
		display.getChildren().add(command);
		return command;
	}
	
	
	protected VBox getVariableDisplay(){
		return myVariableDisplay;
	}
}