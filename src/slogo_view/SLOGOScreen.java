package slogo_view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import slogo_controller.SLOGOController;
import slogo_controller.TurtleController;

public class SLOGOScreen {
	private static final String TURTLE_AREA_OUTLINE = "Black";
	private static final int TITLE_SIZE = 50;
	private static final int MIN_BOUNDARY = 0;
	private static final int TURTLE_AREA_Y = TITLE_SIZE + 5;
	private static final int TURTLE_AREA_X = MIN_BOUNDARY + 5;
	private static final int TURTLE_AREA_WIDTH = 800;
	private static final int DISPLAY_AREA_X = TURTLE_AREA_X + TURTLE_AREA_WIDTH + 10;
	private static final int TEXT_FIELD_X = 5;
	private static final int BUTTON_Y = 12;
	private static final int COMBO_BOX_Y = 25;
	private static final int FONT_SIZE = 20;
	private static final int BACKGROUND_X = 200;
	private static final int CONTROL_X_OFFSET = 150;
	private static final int PEN_X = BACKGROUND_X + CONTROL_X_OFFSET;
	private static final int IMAGE_X = PEN_X + CONTROL_X_OFFSET;
	private static final int LANGUAGES_X = IMAGE_X + CONTROL_X_OFFSET;
	private static final int MULTILINE_X = LANGUAGES_X + CONTROL_X_OFFSET;
	private static final int HELP_X = MULTILINE_X + CONTROL_X_OFFSET - 40;
	private static final int NEW_WINDOW_X = HELP_X + CONTROL_X_OFFSET - 40;
	private static final int PEN_OPTIONS_X = NEW_WINDOW_X + CONTROL_X_OFFSET - 40;
	private static final String DEFAULT_IMAGE = "Turtle";
	private static final String DEFAULT_BACKGROUND = "White";
	private static final String DEFAULT_PEN = "Black";
	private static final String TITLE = "SLOGO";
	static final String[] LANGUAGES = {"Deutsche", "English", "Espanol", "Francais", "Italiano", "Portugues", 
			"Russkiy", "Zhongwen"};
	private static final int HEIGHT = 700;
	private static final int TEXT_FIELD_Y = HEIGHT - 30;
	private static final int TURTLE_AREA_HEIGHT = TEXT_FIELD_Y - 5 - TURTLE_AREA_Y;
	private static final int DISPLAY_HEIGHT = TURTLE_AREA_HEIGHT/3;
	private static final int WIDTH = 1300;
	private static final int DISPLAY_WIDTH = WIDTH - TURTLE_AREA_WIDTH - 50;
	
	private ResourceBundle myResources;
	private Group myRoot;
	private UIBuilder myBuilder;
	private TreeMap<String, Paint> myColorsMap;
	private ObservableList<String> myColors;
	private TreeMap<String, String> myImagesMap;
	private ObservableList<String> myImages;
	private String myLanguage;
	private ObservableList<String> myLanguages;
	private SLOGOController myController;
	private Rectangle myTurtleScreen;
	private Stage myStage;
	private Playground myPlayground;
	private VBox userDefinedCommandsDisplay;
	private VBox commandHistoryDisplay;
	private ComboBox myImageSelector;
	
	private PenOptions myPenOptions;
	private List<String> strokeTypes;
	
	protected SLOGOScreen(Playground playground, Stage stage, ResourceBundle resources, Group root, String language){
		myPlayground = playground;
		myStage = stage;
		myResources = resources;
		myRoot = root;
		myLanguage = language;
	}
	
	public Scene init(){
		myLanguages = FXCollections.observableList(new ArrayList<String>(Arrays.asList(LANGUAGES)));
		setUpImagesMap();
		myImages = FXCollections.observableList(new ArrayList<String>());
		myImages.addAll(myImagesMap.keySet());
		setUpColorsMap();
		myColors = FXCollections.observableList(new ArrayList<String>());
		myColors.addAll(myColorsMap.keySet());
		Scene scene = new Scene(myRoot, WIDTH, HEIGHT);
		myBuilder = new UIBuilder(myRoot);
		myBuilder.addText(TITLE, MIN_BOUNDARY, MIN_BOUNDARY, TITLE_SIZE);
		setUpTurtleScreen();
		setUpComboBoxes();
		setUpHelpButton();
		setUpTextInput();
		setUpCommandHistoryDisplay();
		setUpUserDefinedCommandsDisplay();
		setUpVariableDisplay();
		setUpMultilineButton();
		setUpNewWindowButton();
		setUpPenOptions();
		setUpOptionsButton();
		myStage.setScene(scene);
		myStage.setTitle(TITLE);
		myStage.show();
		myPlayground.updateScreen();
		return scene;
	}
	
	private void setUpUserDefinedCommandsDisplay() {
		userDefinedCommandsDisplay = myBuilder.addScrollableVBox(DISPLAY_AREA_X, TURTLE_AREA_Y +DISPLAY_HEIGHT + 10,
				DISPLAY_WIDTH, DISPLAY_HEIGHT - 10);
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
						configureButtonToRunCommand(s, "user-defined-button", userDefinedCommandsDisplay);
					}
				}
			}
		});
	}
	
	private void removeButtonDuplicates(String s) {
		for (int i = 0; i < userDefinedCommandsDisplay.getChildren().size(); i++) {
			Button button = (Button) (userDefinedCommandsDisplay.getChildren().get(i));
			if (button.getText().equals(s)) {
				userDefinedCommandsDisplay.getChildren().remove(i);
				i--;
			}
		}
	}

	private void setUpTextInput() {
		TextField commandReader = myBuilder.addTextField("", TEXT_FIELD_X, 
				TEXT_FIELD_Y);
		commandReader.setPromptText(myResources.getString("TextFieldText"));
		commandReader.setOnAction(new EventHandler<ActionEvent>() {
			public void handle (ActionEvent event){
				myPlayground.runCommandFromTextInput(commandReader);
			}
		});
		commandReader.setMinWidth(TURTLE_AREA_WIDTH + 360);
	}

	private void setUpHelpButton() {
		myBuilder.addButton(myResources.getString("Help"), HELP_X, BUTTON_Y, new EventHandler<ActionEvent>() {
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

	private void setUpMultilineButton() {
		myBuilder.addButton(myResources.getString("Multiline"), MULTILINE_X, BUTTON_Y, 
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				final Stage dialog = new Stage();
				dialog.initModality(Modality.APPLICATION_MODAL);
				dialog.initOwner(myStage);

				VBox root = new VBox();

				TextArea input = new TextArea();
				Button submit = new Button("Run");
				submit.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						myPlayground.runCommandFromTextInput(input);
					}

				});

				root.getChildren().add(input);
				root.getChildren().add(submit);
				Scene dialogScene = new Scene(root, WIDTH/2, HEIGHT/2);
				dialog.setScene(dialogScene);
				dialog.show();


			}
		});
	}
	
	private void setUpNewWindowButton(){
		myBuilder.addButton(myResources.getString("NewWindow"), NEW_WINDOW_X, BUTTON_Y, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event){
				Playground playground = new Playground(new Stage(), myLanguage);
				TurtleController controller = new TurtleController(playground);
				playground.setController(controller);
				playground.init();
			}
		});
	}
	
	
	private void setUpPenOptions() {
		myPenOptions = new PenOptions(Color.BLACK, 2, 1d, 0d);
		
		strokeTypes = new ArrayList<String>();
		strokeTypes.add(myResources.getString("Dashed"));
		strokeTypes.add(myResources.getString("Dotted"));
		strokeTypes.add(myResources.getString("Solid"));
	}
	
	private void setUpOptionsButton() {
		myBuilder.addButton(myResources.getString("MorePenOptions"), PEN_OPTIONS_X, BUTTON_Y, new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				final Stage options = new Stage();
				options.initModality(Modality.APPLICATION_MODAL);
				options.initOwner(myStage);
				
				VBox root = new VBox();
				ComboBox<String> stroke_types = new ComboBox<String>(FXCollections.observableList(strokeTypes));
				stroke_types.setValue(myResources.getString("Solid"));
				stroke_types.valueProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (newValue.equals(myResources.getString("Solid"))) {
							myPenOptions.setDashLength(1d);
							myPenOptions.setDashSpace(0d);
						} else if (newValue.equals(myResources.getString("Dashed"))) {
							myPenOptions.setDashLength(5d);
							myPenOptions.setDashSpace(5d);
							
						} else if (newValue.equals(myResources.getString("Dotted"))) {
							myPenOptions.setDashLength(0.01d);
							myPenOptions.setDashSpace(5d);
						}
					}
					
				});
				root.getChildren().add(stroke_types);
				Scene scene = new Scene(root, 200, 200);
				options.setScene(scene);
				options.show();
				
			}
			
		});
	}

	private void setUpComboBoxes() {
		myBuilder.addText(myResources.getString("Background"), BACKGROUND_X, MIN_BOUNDARY, FONT_SIZE);
		myBuilder.addComboBox(BACKGROUND_X, COMBO_BOX_Y, myColors, 
				"8. " + myResources.getString(DEFAULT_BACKGROUND), new ChangeListener<String>() {
			public void changed(ObservableValue ov, String t, String t1){
				myTurtleScreen.setFill(myColorsMap.get(t1));
			}
		});
		myBuilder.addText(myResources.getString("Pen"), PEN_X, MIN_BOUNDARY, FONT_SIZE);
		myBuilder.addComboBox(PEN_X, COMBO_BOX_Y, myColors, 
				"1. " + myResources.getString(DEFAULT_PEN), new ChangeListener<String>() {
			public void changed(ObservableValue ov, String t, String t1){
				myPlayground.setPenColor(t1);
			}
		});
		myBuilder.addText(myResources.getString("Image"), IMAGE_X, MIN_BOUNDARY, FONT_SIZE);
		myImageSelector = myBuilder.addComboBox(IMAGE_X, COMBO_BOX_Y, myImages, 
				"1. " + myResources.getString(DEFAULT_IMAGE), new ChangeListener<String>() {
			public void changed(ObservableValue ov, String t, String t1) {
				myPlayground.changeTurtleImages(t1);
			}
		});
		myBuilder.addText(myResources.getString("Languages"), LANGUAGES_X, MIN_BOUNDARY, FONT_SIZE);
		myBuilder.addComboBox(LANGUAGES_X, COMBO_BOX_Y, myLanguages, myLanguage, new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String t, String t1) {
				myPlayground.reinit(myStage, t1);
			}

		});
	}

	private void setUpTurtleScreen() {
		myBuilder.addRectangle(TURTLE_AREA_X, TURTLE_AREA_Y, TURTLE_AREA_WIDTH, TURTLE_AREA_HEIGHT, 
				myColorsMap.get("1. " + myResources.getString(TURTLE_AREA_OUTLINE)));
		myTurtleScreen = myBuilder.addRectangle(TURTLE_AREA_X + 1, TURTLE_AREA_Y + 1, TURTLE_AREA_WIDTH - 2, 
				TURTLE_AREA_HEIGHT - 2, myColorsMap.get("8. " + myResources.getString(DEFAULT_BACKGROUND)));
	}

	private void setUpCommandHistoryDisplay() {
		commandHistoryDisplay = myBuilder.addScrollableVBox(DISPLAY_AREA_X, 
				TURTLE_AREA_Y, DISPLAY_WIDTH, DISPLAY_HEIGHT);
		myPlayground.getHistory().addListener(new ListChangeListener<Object>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void onChanged(ListChangeListener.Change change) {
				while (change.next()) {
					List<String> list = change.getAddedSubList();
					for (String s : list) {
						configureButtonToRunCommand(s, "command-history-button", commandHistoryDisplay);
					}
				}
			}
		});
	}
	
	private void setUpVariableDisplay(){
		VBox variableDisplay = myBuilder.addScrollableVBox(DISPLAY_AREA_X, 
				TURTLE_AREA_Y + 2*TURTLE_AREA_HEIGHT/3 + 10, DISPLAY_WIDTH, DISPLAY_HEIGHT - 10);
		myPlayground.getUserVariables().addListener(new ListChangeListener<Object>() {
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public void onChanged(ListChangeListener.Change change){
				while (change.next()){
					List<String> list = change.getAddedSubList();
					for (String s : list) {
						addButtonToDisplay(s, "command-history-button", variableDisplay);
					}
				}
			}
		});
	}

	private void configureButtonToRunCommand(String text, String id, VBox display) {
		Button command = addButtonToDisplay(text, id, display);
		command.setOnAction(new EventHandler<ActionEvent>() {

			@Override
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
	
	private void setUpImagesMap(){
		myImagesMap = new TreeMap<String, String>();
		myImagesMap.put("1. " + myResources.getString(DEFAULT_IMAGE), "turtle.png");
		myImagesMap.put("2. " + myResources.getString("Rocket"), "rocket.png");
		myImagesMap.put("3. " + myResources.getString("Frog"), "frog.png");
		myImagesMap.put("4. " + myResources.getString("Pencil"), "pencil.png");
		myImagesMap.put("5. " + myResources.getString("Duke"), "duke.png");
	}

	private void setUpColorsMap(){
		myColorsMap = new TreeMap<String, Paint>();
		myColorsMap.put("1. " + myResources.getString(DEFAULT_PEN), Color.BLACK);
		myColorsMap.put("2. " + myResources.getString("Blue"), Color.BLUE);
		myColorsMap.put("3. " + myResources.getString("Brown"), Color.BROWN);
		myColorsMap.put("4. " + myResources.getString("Green"), Color.GREEN);
		myColorsMap.put("5. " + myResources.getString("Orange"), Color.ORANGE);
		myColorsMap.put("6. " + myResources.getString("Purple"), Color.PURPLE);
		myColorsMap.put("7. " + myResources.getString("Red"), Color.RED);
		myColorsMap.put("8. " + myResources.getString(DEFAULT_BACKGROUND), Color.WHITE);
		myColorsMap.put("9. " + myResources.getString("Yellow"), Color.YELLOW);
	}
	
	protected ComboBox<String> getImageSelector(){
		return myImageSelector;
	}
	
	protected TreeMap<String, Paint> getColorsMap(){
		return myColorsMap;
	}
	
	protected TreeMap<String, String> getImagesMap(){
		return myImagesMap;
	}
	
	protected ObservableList<String> getColors(){
		return myColors;
	}
	
	protected ObservableList<String> getImages(){
		return myImages;
	}
	
	protected PenOptions getPenOptions(){
		return myPenOptions;
	}
}
