package slogo_view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import slogo_controller.SLOGOController;
import slogo_model.SLOGOModel;

public class Playground implements SLOGOViewExternal {
	private static final String TURTLE_AREA_OUTLINE = "Black";
	private static final int TITLE_SIZE = 50;
	private static final int MIN_BOUNDARY = 0;
	private static final int TURTLE_AREA_Y = TITLE_SIZE + 5;
	private static final int TURTLE_AREA_X = MIN_BOUNDARY + 5;
	private static final int TURTLE_HEIGHT = 25;
	private static final int TURTLE_AREA_WIDTH = 800;
	private static final int TURTLE_X_OFFSET = TURTLE_AREA_WIDTH/2 + TURTLE_AREA_X;
	private static final int HEIGHT = 700;
	private static final int WIDTH = 1200;
	private static final int TEXT_FIELD_X = 5;
	private static final int TEXT_FIELD_Y = HEIGHT - 30;
	private static final int TURTLE_AREA_HEIGHT = TEXT_FIELD_Y - 5 - TURTLE_AREA_Y;
	private static final int TURTLE_Y_OFFSET = TURTLE_AREA_HEIGHT/2 + TURTLE_AREA_Y;
	private static final int HELP_Y = 12;
	private static final int COMBO_BOX_Y = 25;
	private static final int FONT_SIZE = 20;
	private static final int BACKGROUND_X = 200;
	private static final int CONTROL_X_OFFSET = 150;
	private static final int PEN_X = BACKGROUND_X + CONTROL_X_OFFSET;
	private static final int IMAGE_X = PEN_X + CONTROL_X_OFFSET;
	private static final int LANGUAGES_X = IMAGE_X + CONTROL_X_OFFSET;
	private static final int HELP_X = LANGUAGES_X + CONTROL_X_OFFSET;
	private static final String DEFAULT_IMAGE = "Turtle";
	private static final String DEFAULT_BACKGROUND = "White";
	private static final String DEFAULT_PEN = "Black";
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	private static final String TITLE = "SLOGO";


	private ResourceBundle myResources;
	private Group myRoot;
	private UIBuilder myBuilder;
	private HashMap<String, Paint> myColorsMap;
	private ObservableList<String> myColors;
	private Paint myPenColor;
	private HashMap<String, String> myImagesMap;
	private ObservableList<String> myImages;
	private SLOGOModel myModel;
	private SLOGOController myController;
	private Rectangle myTurtleScreen;
	private ImageView myTurtle;
	private ObservableList<String> myUserCommands;
	private ArrayList<String> myUserVariables;
	private ArrayList<Line> myTrails;
	private ComboBox myPenColorSelector;
	private ComboBox myBackgroundSelector;
	private double myX = 0;
	private double myY = 0;
	private Stage myStage;
	private TextArea commandHistoryTextArea;
	private VBox userDefinedCommandsTextArea;
	private ObservableList<String> userDefinedCommands;
	
	private boolean errorReceived = false;

	public Playground(Stage s, String language) {
		myStage = s;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		setUpColorsMap();
		myColors = FXCollections.observableList(new ArrayList<String>());
		myColors.addAll(myColorsMap.keySet());
		myPenColor = Color.BLACK;
		setUpImagesMap();
		myImages = FXCollections.observableList(new ArrayList<String>());
		myImages.addAll(myImagesMap.keySet());
		myUserCommands = FXCollections.observableList(new ArrayList<String>());
		myUserVariables = new ArrayList<String>();
		myTrails = new ArrayList<Line>();
		userDefinedCommands = FXCollections.observableList(new ArrayList<String>());
	}

	private void setUpImagesMap(){
		myImagesMap = new HashMap<String, String>();
		myImagesMap.put(myResources.getString(DEFAULT_IMAGE), "turtle.png");
		myImagesMap.put(myResources.getString("Rocket"), "rocket.png");
		myImagesMap.put(myResources.getString("Frog"), "frog.png");
		myImagesMap.put(myResources.getString("Pencil"), "pencil.png");
		myImagesMap.put(myResources.getString("Duke"), "duke.png");
	}

	private void setUpColorsMap(){
		myColorsMap = new HashMap<String, Paint>();
		myColorsMap.put(myResources.getString(DEFAULT_PEN), Color.BLACK);
		myColorsMap.put(myResources.getString("Blue"), Color.BLUE);
		myColorsMap.put(myResources.getString("Brown"), Color.BROWN);
		myColorsMap.put(myResources.getString("Green"), Color.GREEN);
		myColorsMap.put(myResources.getString("Orange"), Color.ORANGE);
		myColorsMap.put(myResources.getString("Purple"), Color.PURPLE);
		myColorsMap.put(myResources.getString("Red"), Color.RED);
		myColorsMap.put(myResources.getString(DEFAULT_BACKGROUND), Color.WHITE);
		myColorsMap.put(myResources.getString("Yellow"), Color.YELLOW);
	}

	public Scene init(){
		myRoot = new Group();
		Scene scene = new Scene(myRoot, WIDTH, HEIGHT);
		myBuilder = new UIBuilder(myRoot);
		myBuilder.addText(TITLE, MIN_BOUNDARY, MIN_BOUNDARY, TITLE_SIZE);
		setUpTurtleScreen();
		
		// TODO we should be able to initialize even if there is no model, in which case there is no turtle. set up the turtle when a new turtle is added instead
		setUpTurtle();
		setUpComboBoxes();
		setUpHelpButton();
		setUpTextInput();
		setUpCommandHistoryScreen();
		setUpUserDefinedCommandsDisplay();
		myStage.setScene(scene);
		myStage.setTitle(TITLE);
		myStage.show();
		return scene;
	}

	private void setUpUserDefinedCommandsDisplay() {
		userDefinedCommandsDispay = myBuilder.addTextArea(TURTLE_AREA_X + TURTLE_AREA_WIDTH + 10, TURTLE_AREA_Y + TURTLE_AREA_HEIGHT/2 + 10, 350, TURTLE_AREA_HEIGHT/2 - 10);
		
		userDefinedCommands.addListener(new ListChangeListener<Object>() {

			@Override
			public void onChanged(ListChangeListener.Change change) {
				while (change.next()) {
					List<String> list = change.getAddedSubList();
					for (String s : list) {
						userDefinedCommandsTextArea.appendText(s + "\n");
					}
				}
			}
		});
	}
	
	private void setUpTextInput() {
		TextField commandReader = myBuilder.addTextField("", TEXT_FIELD_X, 
				TEXT_FIELD_Y);
		commandReader.setPromptText(myResources.getString("TextFieldText"));
		commandReader.setOnAction(new EventHandler<ActionEvent>() {
			public void handle (ActionEvent event){
				myController.run(commandReader.getCharacters().toString());
				if (errorReceived) {
					commandReader.setText("");
					errorReceived = false;
					return;
				}
				commandHistoryTextArea.appendText(commandReader.getCharacters().toString() + "\n");
				commandReader.setText("");
			}
		});
		commandReader.setMinWidth(TURTLE_AREA_WIDTH + 360);
	}

	private void setUpHelpButton() {
		myBuilder.addButton(myResources.getString("Help"), HELP_X, HELP_Y, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(myStage);
                
                VBox root = new VBox();
                
                final WebView browser = new WebView();
                final WebEngine webEngine = browser.getEngine();
                ScrollPane dialogPane = new ScrollPane();
                dialogPane.setContent(browser);
                webEngine.loadContent("<h1>Enter help content here.</h1>");
                
                
                root.getChildren().add(dialogPane);
                Scene dialogScene = new Scene(root, WIDTH, HEIGHT);
                dialog.setScene(dialogScene);
                dialog.show();
			}
		});
	}

	private void setUpComboBoxes() {
		myBuilder.addText(myResources.getString("Background"), BACKGROUND_X, MIN_BOUNDARY, FONT_SIZE);
		myBuilder.addComboBox(BACKGROUND_X, COMBO_BOX_Y, myColors, myResources.getString(DEFAULT_BACKGROUND), 
				new ChangeListener<String>() {
			public void changed(ObservableValue ov, String t, String t1){
				myTurtleScreen.setFill(myColorsMap.get(t1));
			}
		});
		myBuilder.addText(myResources.getString("Pen"), PEN_X, MIN_BOUNDARY, FONT_SIZE);
		myPenColorSelector = myBuilder.addComboBox(PEN_X, COMBO_BOX_Y, myColors, 
				myResources.getString(DEFAULT_PEN), new ChangeListener<String>() {
			public void changed(ObservableValue ov, String t, String t1){
				myPenColor = myColorsMap.get(t1);
			}
		});
		myBuilder.addText(myResources.getString("Image"), IMAGE_X, MIN_BOUNDARY, FONT_SIZE);
		myBackgroundSelector = myBuilder.addComboBox(IMAGE_X, COMBO_BOX_Y, myImages, 
				myResources.getString(DEFAULT_IMAGE), new ChangeListener<String>() {
			public void changed(ObservableValue ov, String t, String t1) {
				myTurtle.setImage(new Image(getClass().getClassLoader().getResourceAsStream(myImagesMap.get(t1))));
			}
		});
		//myBuilder.addText(myResources.getString(""), LANGUAGES_X, MIN_BOUNDARY, FONT_SIZE);
		//myLanguageSelector = myBuilder.addComboBox(LANGUAGES_X, COMBO_BOX_Y, items, "ENGLISH", listener)
	}

	private void setUpTurtle() {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(myImagesMap.get(
				myResources.getString(DEFAULT_IMAGE))));
		myTurtle = new ImageView(image);
		myTurtle.setFitHeight(TURTLE_HEIGHT);
		myTurtle.setPreserveRatio(true);
		// TODO let the turtle start from a different position if the model has a different initial position
		// currently this works because the model and view both happen to start from 0,0, but what if the model told it to start somewhere else?
		myTurtle.relocate(TURTLE_X_OFFSET - myTurtle.getBoundsInLocal().getWidth()/2, 
				TURTLE_Y_OFFSET - myTurtle.getBoundsInLocal().getHeight()/2);
		myRoot.getChildren().add(myTurtle);
	}

	private void setUpTurtleScreen() {
		myBuilder.addRectangle(TURTLE_AREA_X, TURTLE_AREA_Y, TURTLE_AREA_WIDTH, TURTLE_AREA_HEIGHT, 
				myColorsMap.get(myResources.getString(TURTLE_AREA_OUTLINE)));
		myTurtleScreen = myBuilder.addRectangle(TURTLE_AREA_X + 1, TURTLE_AREA_Y + 1, TURTLE_AREA_WIDTH - 2, 
				TURTLE_AREA_HEIGHT - 2, myColorsMap.get(myResources.getString(DEFAULT_BACKGROUND)));
	}
	
	private void setUpCommandHistoryScreen() {
		commandHistoryTextArea = myBuilder.addTextArea(TURTLE_AREA_X + TURTLE_AREA_WIDTH + 10, TURTLE_AREA_Y, 350, TURTLE_AREA_HEIGHT/2);
	}

	@Override
	public void showError(String errorMessage) {
		errorReceived = true;
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(myResources.getString("ErrorTitle"));
		alert.setContentText(errorMessage);
		alert.showAndWait();
	}

	@Override
	public ResourceBundle getResourceBundle() {
		return myResources;
	}

	@Override
	public void clearTrails() {
		for (Line line: myTrails){
			myRoot.getChildren().remove(line);
		}
		myTrails.clear();
	}

	@Override
	public void addUserCommand(String userCommand) {
		myUserCommands.add(userCommand);
	}

	@Override
	public void addUserVariable(String userVariable) {
		myUserVariables.add(userVariable);
	}

	@Override
	public SLOGOController getController(){
		return myController;
	}

	@Override
	public double getMaxX(){
		return TURTLE_AREA_WIDTH/2;
	}

	@Override
	public double getMaxY(){
		return TURTLE_AREA_HEIGHT/2;
	}

	@Override
	public void setPenColor(String color){
		myPenColor = myColorsMap.get(color);
		myPenColorSelector.setValue(color);
	}

	@Override
	public void setBackgroundColor(String color){
		myTurtleScreen.setFill(myColorsMap.get(color));
		myBackgroundSelector.setValue(color);
	}

	@Override
	public void setController(SLOGOController controller){
		myController = controller;
	}

	@Override
	public void addModel(SLOGOModel model){
		// TODO make this a list so multiple models can be represented on same view
		myModel = model;
	}

	@Override
	public void updateScreen(){
		myTurtle.relocate(myModel.xCor() + TURTLE_X_OFFSET - myTurtle.getBoundsInLocal().getWidth()/2, 
				TURTLE_Y_OFFSET - myModel.yCor() - myTurtle.getBoundsInLocal().getHeight()/2);
		myTurtle.setRotate(myModel.heading());
		if (myModel.isPenDown() == 1){
			Line line = myBuilder.addLine(myX + TURTLE_X_OFFSET, TURTLE_Y_OFFSET - myY, 
					myModel.xCor() + TURTLE_X_OFFSET, 
					TURTLE_Y_OFFSET - myModel.yCor(), myPenColor);
			myTrails.add(line);
		}
		myX = myModel.xCor();
		myY = myModel.yCor();
		if (myModel.showing() == 0 && myRoot.getChildren().contains(myTurtle)){
			myRoot.getChildren().remove(myTurtle);
		}
		else if (myModel.showing() == 1 && !(myRoot.getChildren().contains(myTurtle))){
			myRoot.getChildren().add(myTurtle);
		}

	}
}
