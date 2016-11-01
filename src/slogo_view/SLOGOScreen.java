package slogo_view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SLOGOScreen {
	private static final int SLIDER_DEFAULT = 1;
	private static final int SLIDER_MAX = 2;
	private static final double SLIDER_MIN = 0.1;
	private static final String TURTLE_AREA_OUTLINE = "Black";
	private static final int TITLE_SIZE = 50;
	private static final int MIN_BOUNDARY = 0;
	private static final int TURTLE_AREA_Y = TITLE_SIZE + 5;
	private static final int HISTORY_Y = TURTLE_AREA_Y + 25;
	private static final int TURTLE_AREA_X = MIN_BOUNDARY + 5;
	private static final int TURTLE_AREA_WIDTH = 800;
	private static final int DISPLAY_AREA_X = TURTLE_AREA_X + TURTLE_AREA_WIDTH + 10;
	private static final int TEXT_FIELD_X = 5;
	private static final int RUN_X = TEXT_FIELD_X + TURTLE_AREA_WIDTH + 10;
	private static final int SLIDER_FONT_SIZE = 10;
	private static final int CONTROL_X_OFFSET = 150;
	private static final int SLIDER_X = RUN_X + CONTROL_X_OFFSET - 40;
	private static final String DEFAULT_BACKGROUND = "White";
	private static final String TITLE = "SLOGO";
	private static final int HEIGHT = 700;
	private static final int TEXT_FIELD_Y = HEIGHT - 30;
	private static final int TURTLE_AREA_HEIGHT = TEXT_FIELD_Y - 5 - TURTLE_AREA_Y;
	private static final int VARIABLE_DISPLAY_Y = TURTLE_AREA_Y + 2*TURTLE_AREA_HEIGHT/3 + 10;
	private static final int DISPLAY_HEIGHT = TURTLE_AREA_HEIGHT/3 - 15;
	private static final double COMMAND_DISPLAY_Y = TURTLE_AREA_Y +DISPLAY_HEIGHT + 32.5;
	private static final int WIDTH = 1300;
	private static final int DISPLAY_WIDTH = WIDTH - TURTLE_AREA_WIDTH - 50;
	
	private Group myRoot;
	private UIBuilder myBuilder;
	private ResourceBundle myResources;
	private Playground myPlayground;
	private TextField myCommandReader;
	private Rectangle myTurtleScreen;
	private Stage myStage;
	private VBox myUserDefinedCommandsDisplay;
	private VBox myCommandHistoryDisplay;
	private Text mySpeedText;
	private ArrayList<Button> myVariableButtons = new ArrayList<Button>();
	private Slider mySlider;
	private SLOGOScreenButtons myButtons = new SLOGOScreenButtons(this);
	private VBox myVariableDisplay;
	public SLOGOScreenComboBoxes data = new SLOGOScreenComboBoxes(this);

	protected SLOGOScreen(Playground playground, Stage stage, ResourceBundle resources, Group root, String language){
		myPlayground = playground;
		myStage = stage;
		myResources = resources;
		myRoot = root;
		data.myLanguage = language;
	}
	
	protected Scene init(){
		Scene scene = new Scene(myRoot, WIDTH, HEIGHT);
		myBuilder = new UIBuilder(myRoot);
		myBuilder.addText(TITLE, MIN_BOUNDARY, MIN_BOUNDARY, TITLE_SIZE);
		data.setUpComboBoxes();
		setUpTurtleScreen();
		setUpTextInput();
		setUpSpeedSlider();
		setUpCommandHistoryDisplay();
		setUpUserDefinedCommandsDisplay();
		setUpVariableDisplay();
		myButtons.setUpButtons();
		scene.setOnMouseClicked(e -> myPlayground.handleMouseInput(e.getX(), e.getY()));
		myStage.setScene(scene);
		myStage.setTitle(TITLE);
		myStage.show();
		myPlayground.updateScreen();
		return scene;
	}

	private void setUpTextInput() {
		myCommandReader = myBuilder.addTextField(myResources.getString("TextFieldText"), TEXT_FIELD_X, 
				TEXT_FIELD_Y);
		myCommandReader.setOnAction(new EventHandler<ActionEvent>() {
			public void handle (ActionEvent event){
				myPlayground.runCommandFromTextInput(myCommandReader);
			}
		});
		myCommandReader.setMinWidth(TURTLE_AREA_WIDTH);
		myButtons.setUpRunButton();
	}
	
	private void setUpSpeedSlider() {
		mySpeedText = myBuilder.addText(myResources.getString("Speed"), SLIDER_X, TEXT_FIELD_Y, SLIDER_FONT_SIZE);
		mySlider = myBuilder.addSlider(SLIDER_X + mySpeedText.getBoundsInLocal().getWidth(), TEXT_FIELD_Y, 
				SLIDER_MIN, SLIDER_MAX, SLIDER_DEFAULT);
	}

	private void setUpTurtleScreen() {
		Rectangle rec = myBuilder.addRectangle(TURTLE_AREA_X, TURTLE_AREA_Y, TURTLE_AREA_WIDTH, TURTLE_AREA_HEIGHT, 
				data.myColorsMap.get("1. " + myResources.getString(TURTLE_AREA_OUTLINE)));
		myTurtleScreen = myBuilder.addRectangle(TURTLE_AREA_X + 1, TURTLE_AREA_Y + 1, TURTLE_AREA_WIDTH - 2, 
				TURTLE_AREA_HEIGHT - 2, data.myColorsMap.get("8. " + myResources.getString(DEFAULT_BACKGROUND)));
		myTurtleScreen.toBack();
		rec.toBack();
	}

	private void setUpCommandHistoryDisplay() {
		myCommandHistoryDisplay = myBuilder.addScrollableVBox(DISPLAY_AREA_X, HISTORY_Y, DISPLAY_WIDTH, 
				DISPLAY_HEIGHT);
		myPlayground.getHistory().addListener(new ListChangeListener<Object>() {
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
		myUserDefinedCommandsDisplay = myBuilder.addScrollableVBox(DISPLAY_AREA_X, COMMAND_DISPLAY_Y,
				DISPLAY_WIDTH, DISPLAY_HEIGHT);
		myPlayground.getUserCommands().addListener(new ListChangeListener<Object>() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void onChanged(ListChangeListener.Change change) {
				while (change.next()) {
					List<String> list = change.getAddedSubList();
					for (String s : list) {
						if (myPlayground.getUserCommands().indexOf(s) == myPlayground.getUserCommands().lastIndexOf(s)) {
							myPlayground.getUserCommands().remove(myPlayground.getUserCommands().indexOf(s));
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
		myVariableDisplay = myBuilder.addScrollableVBox(DISPLAY_AREA_X, VARIABLE_DISPLAY_Y, DISPLAY_WIDTH, 
				DISPLAY_HEIGHT);
		myPlayground.getUserVariables().addListener(new ListChangeListener<Object>() {
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
		for (String oldVariable: myPlayground.getOldVariables()){
			for (Button currButton: myVariableButtons){
				if (currButton.getText().equals(oldVariable)){
					variableDisplay.getChildren().remove(currButton);
					myVariableButtons.remove(currButton);
					myPlayground.getOldVariables().remove(oldVariable);
					return;
				}
			}
		}
	}

	private void configureButtonToRunCommand(String text, String id, VBox display) {
		Button command = addButtonToDisplay(text, id, display);
		command.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				myPlayground.getController().run(text);
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
	
	protected void changeLanguage(String language){
		data.setLanguage(language);
		myPlayground.changeLanguage(data.myLanguage);
		myResources = myPlayground.getResourceBundle();
		mySpeedText.setText(myResources.getString("Speed"));
		mySlider.setLayoutX(SLIDER_X + mySpeedText.getBoundsInLocal().getWidth());
		myCommandReader.setText(myResources.getString("TextFieldText"));
		myButtons.changeLanguage();
		data.changeLanguages();
	}
	
	protected Rectangle getTurtleArea(){
		return myTurtleScreen;
	}
	
	protected ComboBox<String> getBackgroundSelector(){
		return data.myBackgroundSelector;
	}
	
	protected ComboBox<String> getPenColorSelector(){
		return data.myPenColorSelector;
	}

	protected ComboBox<Integer> getPenWidthSelector(){
		return data.myPenWidthSelector;
	}
	
	protected Slider getSpeedSlider(){
		return mySlider;
	}
	
	protected VBox getVariableDisplay(){
		return myVariableDisplay;
	}
	
	protected ResourceBundle getResources(){
		return myResources;
	}
	
	protected UIBuilder getBuilder(){
		return myBuilder;
	}
	
	protected Playground getPlayground(){
		return myPlayground;
	}
	
	protected TextField getTextField(){
		return myCommandReader;
	}
	
	protected Stage getStage(){
		return myStage;
	}
	
	protected SLOGOScreenComboBoxes getComboBoxesData(){
		return data;
	}
}