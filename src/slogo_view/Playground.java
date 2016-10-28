package slogo_view;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import slogo_controller.SLOGOController;
import slogo_model.SLOGOModel;

public class Playground implements SLOGOView, Observer{
	private static final int TITLE_SIZE = 50;
	private static final int MIN_BOUNDARY = 0;
	private static final int TURTLE_AREA_Y = TITLE_SIZE + 5;
	private static final int TURTLE_AREA_X = MIN_BOUNDARY + 5;
	private static final int TURTLE_AREA_WIDTH = 800;
	private static final int TURTLE_X_OFFSET = TURTLE_AREA_WIDTH/2 + TURTLE_AREA_X;
	private static final int HEIGHT = 700;
	private static final int TEXT_FIELD_Y = HEIGHT - 30;
	private static final int TURTLE_AREA_HEIGHT = TEXT_FIELD_Y - 5 - TURTLE_AREA_Y;
	private static final int TURTLE_Y_OFFSET = TURTLE_AREA_HEIGHT/2 + TURTLE_AREA_Y;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	static final String[] LANGUAGES = {"Deutsche", "English", "Espanol", "Francais", "Italiano", "Portugues", 
			"Russkiy", "Zhongwen"};
	private static final int TURTLE_HEIGHT = 25;


	private ResourceBundle myResources;
	private Group myRoot;
	private UIBuilder myBuilder;
	private Paint myPenColor;
	private SLOGOController myController;
	private Rectangle myTurtleScreen;
	private List<ImageView> visualTurtles;
	private ObservableList<String> myUserVariables;
	private ArrayList<Line> myTrails;
	private ComboBox<String> myPenColorSelector;
	private ComboBox<String> myBackgroundSelector;
	private ComboBox<String> myImageSelector;
	private ArrayList<Double> myX = new ArrayList<Double>();
	private ArrayList<Double> myY = new ArrayList<Double>();
	private Stage myStage;
	private SLOGOScreen myScreen;
	private ObservableList<String> myCommandHistory;

	private ObservableList<String> myUserCommands;

	private boolean errorReceived = false;

	public Playground(Stage s, String language) {
		construct(s, language);
	}

	private void construct(Stage s, String language) {
		myStage = s;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		myPenColor = Color.BLACK;
		myUserCommands = FXCollections.observableList(new ArrayList<String>());
		myCommandHistory = FXCollections.observableList(new ArrayList<String>());
		myUserVariables = FXCollections.observableList(new ArrayList<String>());
		myTrails = new ArrayList<Line>();
		visualTurtles = new ArrayList<ImageView>();
		myRoot = new Group();
		myBuilder = new UIBuilder(myRoot);
		myScreen = new SLOGOScreen(this, myStage, myResources, myRoot, language);
	}
	
	public void init(){
		myScreen.init();
	}

	protected void reinit(Stage s, String language) {
		construct(s, language);
		myController.changeLanguage();
		init();
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
	public void addUserVariable(String userVariable, double value) {
		StringBuilder sb = new StringBuilder(userVariable);
		sb.append(" = ");
		sb.append(value);
		myUserVariables.add(sb.toString());
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
	public void setPenColor(int index){
		setPenColor(myScreen.getColors().get(index - 1));
		myPenColorSelector.setValue(myScreen.getColors().get(index - 1));
	}
	
	protected void setPenColor(String color){
		myPenColor = myScreen.getColorsMap().get(color);
	}

	@Override
	public void setBackgroundColor(int index){
		myTurtleScreen.setFill(myScreen.getColorsMap().get(myScreen.getColors().get(index)));
		myBackgroundSelector.setValue(myScreen.getColors().get(index));
	}
	
	@Override
	public void setImage(int index){
		changeTurtleImages(myScreen.getImages().get(index));
		myScreen.getImageSelector().setValue(myScreen.getImages().get(index));
	}

	@Override
	public void setController(SLOGOController controller){
		myController = controller;
	}
	
	private void setUpTurtle() {
		Image image = new Image(getClass().getClassLoader().getResourceAsStream(myScreen.getImagesMap()
				.get(myScreen.getImageSelector().getValue())));
		ImageView visT = new ImageView(image);
		visT.setFitHeight(TURTLE_HEIGHT);
		visT.setPreserveRatio(true);
		myX.add(0.0);
		myY.add(0.0);
		myRoot.getChildren().add(visT);
		visualTurtles.add(visT);
	}

	@Override
	public void updateScreen(){
		int numModels = myController.getModels().size();
		while(visualTurtles.size() < numModels){
			setUpTurtle();
		}
		
		for(int i = 0; i < numModels; i++){
			ImageView myTurtle = visualTurtles.get(i);
			SLOGOModel myModel = myController.getModels().get(i);
			
			myTurtle.relocate(myModel.xCor() + TURTLE_X_OFFSET - myTurtle.getBoundsInLocal().getWidth()/2, 
					TURTLE_Y_OFFSET - myModel.yCor() - myTurtle.getBoundsInLocal().getHeight()/2);
			myTurtle.setRotate(myModel.heading());
			if (myModel.isPenDown() == 1){
				Line line = myBuilder.addLine(myX.get(i) + TURTLE_X_OFFSET, TURTLE_Y_OFFSET - myY.get(i), 
						myModel.xCor() + TURTLE_X_OFFSET, 
						TURTLE_Y_OFFSET - myModel.yCor(), myPenColor);
				myTrails.add(line);
			}
			myX.set(i, myModel.xCor());
			myY.set(i, myModel.yCor());
			if (myModel.showing() == 0 && myRoot.getChildren().contains(myTurtle)){
				myRoot.getChildren().remove(myTurtle);
			}
			else if (myModel.showing() == 1 && !(myRoot.getChildren().contains(myTurtle))){
				myRoot.getChildren().add(myTurtle);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		updateScreen();
	}
	
	protected void runCommandFromTextInput(TextInputControl tic) {
		tic.getText();
		myController.run(tic.getText());
		if (errorReceived) {
			tic.setText("");
			errorReceived = false;
			return;
		}
		myCommandHistory.add(tic.getText());
		tic.setText("");
	}
	
	protected ObservableList<String> getUserCommands(){
		return myUserCommands;
	}
	
	protected ObservableList<String> getUserVariables(){
		return myUserVariables;
	}
	
	protected ObservableList<String> getHistory(){
		return myCommandHistory;
	}
	
	protected void changeTurtleImages(String image){
		visualTurtles.forEach(visT ->  visT.setImage(new Image(getClass().getClassLoader()
				.getResourceAsStream(myScreen.getImagesMap().get(image)))));
	}
}
