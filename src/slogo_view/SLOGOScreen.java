package slogo_view;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
	private static final int TURTLE_AREA_X = MIN_BOUNDARY + 5;
	private static final int TURTLE_AREA_WIDTH = 800;
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
	private static final int WIDTH = 1300;
	
	private Group myRoot;
	private UIBuilder myBuilder;
	private ResourceBundle myResources;
	private Playground myPlayground;
	private TextField myCommandReader;
	private Rectangle myTurtleScreen;
	private Stage myStage;
	private Slider mySlider;
	public Text mySpeedText;
	private SLOGOScreenButtons myButtons = new SLOGOScreenButtons(this);
	private SLOGOScreenDisplays myDisplays = new SLOGOScreenDisplays(this);
	public SLOGOScreenComboBoxes myComboBoxes = new SLOGOScreenComboBoxes(this);

	protected SLOGOScreen(Playground playground, Stage stage, ResourceBundle resources, Group root, String language){
		myPlayground = playground;
		myStage = stage;
		myResources = resources;
		myRoot = root;
		myComboBoxes.setLanguage(language);
	}
	
	protected Scene init(){
		Scene scene = new Scene(myRoot, WIDTH, HEIGHT);
		myBuilder = new UIBuilder(myRoot);
		myBuilder.addText(TITLE, MIN_BOUNDARY, MIN_BOUNDARY, TITLE_SIZE);
		myComboBoxes.setUpComboBoxes();
		setUpTurtleScreen();
		setUpTextInput();
		setUpSpeedSlider();
		myDisplays.setUpDisplays();
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
				myComboBoxes.getColorsMap().get("1. " + myResources.getString(TURTLE_AREA_OUTLINE)));
		myTurtleScreen = myBuilder.addRectangle(TURTLE_AREA_X + 1, TURTLE_AREA_Y + 1, TURTLE_AREA_WIDTH - 2, 
				TURTLE_AREA_HEIGHT - 2, myComboBoxes.getColorsMap().get("8. " + myResources.getString(DEFAULT_BACKGROUND)));
		myTurtleScreen.toBack();
		rec.toBack();
	}
	
	protected void changeLanguage(String language){
		myComboBoxes.setLanguage(language);
		myPlayground.changeLanguage(myComboBoxes.getLanguage());
		myResources = myPlayground.getResourceBundle();
		mySpeedText.setText(myResources.getString("Speed"));
		mySlider.setLayoutX(SLIDER_X + mySpeedText.getBoundsInLocal().getWidth());
		myCommandReader.setText(myResources.getString("TextFieldText"));
		myButtons.changeLanguage();
		myComboBoxes.changeLanguages();
	}
	
	protected Rectangle getTurtleArea(){
		return myTurtleScreen;
	}
	
	protected Slider getSpeedSlider(){
		return mySlider;
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
		return myComboBoxes;
	}
	
	protected SLOGOScreenDisplays getDisplaysData(){
		return myDisplays;
	}
}