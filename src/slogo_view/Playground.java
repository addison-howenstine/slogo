package slogo_view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
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
	private static final int WIDTH = 1300;
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
	private HashMap<String, Double> myVariableMap = new HashMap<String, Double>();
	private ArrayList<String> myOldVariables = new ArrayList<String>();
	private Canvas myCanvas;
	private boolean errorReceived = false;
	private ArrayList<Animation> myAnimations = new ArrayList<Animation>();
	private int counter = 0;
	private ArrayList<Double> myHeadings = new ArrayList<Double>();
	private boolean clearScreen = false;


	public Playground(Stage stage, String language) {
		construct(stage, language);
	}

	private void construct(Stage s, String language) {
		myStage = s;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		myUserCommands = FXCollections.observableList(new ArrayList<String>());
		myCommandHistory = FXCollections.observableList(new ArrayList<String>());
		myUserVariables = FXCollections.observableList(new ArrayList<String>());
		myTrails = new ArrayList<Line>();
		visualTurtles = new ArrayList<ImageView>();
		myRoot = new Group();
		myBuilder = new UIBuilder(myRoot);
		myScreen = new SLOGOScreen(this, myStage, myResources, myRoot, language);
		myCanvas = new Canvas(WIDTH, HEIGHT);
		myRoot.getChildren().add(myCanvas);
	}

	
	public void init(){
		myScreen.init();
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
		myAnimations.add(null);
	}

	@Override
	public void addUserCommand(String userCommand) {
		myUserCommands.add(userCommand);
	}

	@Override
	public void addUserVariable(String userVariable, double value) {
		if (myVariableMap.containsKey(userVariable)){
			Double oldValue = myVariableMap.get(userVariable);
			StringBuilder sb = new StringBuilder(userVariable);
			sb.append(" = ");
			sb.append(oldValue);
			myOldVariables.add(sb.toString());
		}
		myVariableMap.put(userVariable, value);
		StringBuilder sb2 = new StringBuilder(userVariable);
		sb2.append(" = ");
		sb2.append(value);
		myUserVariables.add(sb2.toString());
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
		myScreen.getPenOptions().setColor(myScreen.getColorsMap().get(color));
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
		visT.setTranslateX(TURTLE_X_OFFSET - visT.getBoundsInLocal().getWidth()/2); 
		visT.setTranslateY(TURTLE_Y_OFFSET - visT.getBoundsInLocal().getHeight()/2);
		myX.add(0.0);
		myY.add(0.0);
		myRoot.getChildren().add(visT);
		visualTurtles.add(visT);
		myHeadings.add(0.0);
	}

	@Override
	public void updateScreen(){
		GraphicsContext gc = myCanvas.getGraphicsContext2D();
		int numModels = myController.getModels().size();
		while(visualTurtles.size() < numModels){
			setUpTurtle();
		}
		for(int i = 0; i < numModels; i++){
			ImageView myTurtle = visualTurtles.get(i);
			SLOGOModel myModel = myController.getModels().get(i);
			if (myX.get(i) != myModel.xCor() || myY.get(i) != myModel.yCor()){
				Path path = new Path();
				path.getElements().add(new MoveTo(myX.get(i) + TURTLE_X_OFFSET, TURTLE_Y_OFFSET - myY.get(i)));
				path.getElements().add(new LineTo(myModel.xCor() + TURTLE_X_OFFSET, 
						TURTLE_Y_OFFSET - myModel.yCor()));
				PathTransition pt = new PathTransition(Duration.millis(1000), path, myTurtle);
				int j = i;
				if (myModel.isPenDown() == 1){
					pt.currentTimeProperty().addListener(new ChangeListener<Duration>() {
						double oldX = myTurtle.getTranslateX() + myTurtle.getBoundsInLocal().getWidth()/2;
						double oldY = myTurtle.getTranslateY() + myTurtle.getBoundsInLocal().getHeight()/2;
						double newX = myTurtle.getTranslateX() + myTurtle.getBoundsInLocal().getWidth()/2;
						double newY = myTurtle.getTranslateY() + myTurtle.getBoundsInLocal().getHeight()/2;
						double count = -1;
						public void changed(ObservableValue ov, Duration t, Duration t1){
							count++;
							if (t.equals(Duration.ZERO))
								return;
							if (count > 2){
								gc.setStroke(myScreen.getPenOptions().getColor());
								gc.setLineWidth(myScreen.getPenOptions().getWidth());
								gc.setLineDashes(myScreen.getPenOptions().getDashLength(), 
										myScreen.getPenOptions().getDashSpace());
								gc.strokeLine(oldX, oldY, newX, newY);
							}
							oldX = newX;
							oldY = newY;
							newX = myTurtle.getTranslateX() + myTurtle.getBoundsInLocal().getWidth()/2;
							newY = myTurtle.getTranslateY() + myTurtle.getBoundsInLocal().getHeight()/2;
						}
					});
				}
				myX.set(j, myModel.xCor());
				myY.set(j, myModel.yCor());
				myAnimations.add(pt);
			}
			if (myHeadings.get(i) != myModel.heading()){
				RotateTransition rt = new RotateTransition(Duration.seconds(1));
				rt.setNode(myTurtle);
				rt.setToAngle(myModel.heading());
				myAnimations.add(rt);
				myHeadings.set(i, myModel.heading());
			}
			if (myModel.showing() == 0 && myRoot.getChildren().contains(myTurtle)){
				myRoot.getChildren().remove(myTurtle);
			}
			else if (myModel.showing() == 1 && !(myRoot.getChildren().contains(myTurtle))){
				myRoot.getChildren().add(myTurtle);
			}
		}
		if (myAnimations.size() > 0)
			runAnimation(myAnimations.get(counter), counter + 1);
	}

	@Override
	public void update(Observable o, Object arg) {
		updateScreen();
	}
	
	private void runAnimation(Animation animation, int index){
		if (animation == null){
			myCanvas.getGraphicsContext2D().clearRect(TURTLE_AREA_X, TURTLE_AREA_Y, TURTLE_AREA_WIDTH, TURTLE_AREA_HEIGHT);
			animationsChecker(index);
		}
		else {
			animation.play();
			animation.setOnFinished(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event){
					animationsChecker(index);
				}
			});
		}
	}
	
	private void animationsChecker(int index) {
		if (index < myAnimations.size()){
			runAnimation(myAnimations.get(index), index + 1);
			counter++;
		}
		else {
			myAnimations.clear();
			counter = 0;
		}
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
	
	protected ArrayList<String> getOldVariables(){
		return myOldVariables;
	}
	
	protected ObservableList<String> getHistory(){
		return myCommandHistory;
	}
	
	protected void changeTurtleImages(String image){
		visualTurtles.forEach(visT ->  visT.setImage(new Image(getClass().getClassLoader()
				.getResourceAsStream(myScreen.getImagesMap().get(image)))));
		}
	
	protected void changeLanguage(String language){
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		myController.changeLanguage();
	}
}
