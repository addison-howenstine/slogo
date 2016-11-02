package slogo_view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import slogo_controller.SLOGOController;
import slogo_model.SLOGOModel;

public class Playground implements SLOGOView, Observer{
	private static final int TITLE_SIZE = 50;
	private static final int TURTLE_AREA_Y = TITLE_SIZE + 5;
	private static final int TURTLE_AREA_WIDTH = 800;
	private static final int HEIGHT = 700;
	private static final int TEXT_FIELD_Y = HEIGHT - 30;
	private static final int TURTLE_AREA_HEIGHT = TEXT_FIELD_Y - 5 - TURTLE_AREA_Y;
	private static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
	static final String[] LANGUAGES = {"Deutsche", "English", "Espanol", "Francais", "Italiano", "Portugues", 
		"Russkiy", "Zhongwen"};


	private ResourceBundle myResources;
	private Group myRoot;
	private SLOGOController myController;
	private ObservableList<String> myUserVariables;
	private ArrayList<Double> myX = new ArrayList<Double>();
	private ArrayList<Double> myY = new ArrayList<Double>();
	private Stage myStage;
	private SLOGOScreen myScreen;
	private ObservableList<String> myCommandHistory;
	private ObservableList<String> myUserCommands;
	private HashMap<String, Double> myVariableMap = new HashMap<String, Double>();
	private ArrayList<String> myOldVariables = new ArrayList<String>();
	private boolean errorReceived = false;
	private FrontEndTurtles myFrontEndTurtles;
	private ArrayList<Double> myHeadings = new ArrayList<Double>();


	public Playground(Stage stage, String language) {
		myStage = stage;
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		myUserCommands = FXCollections.observableList(new ArrayList<String>());
		myCommandHistory = FXCollections.observableList(new ArrayList<String>());
		myUserVariables = FXCollections.observableList(new ArrayList<String>());
		myRoot = new Group();
		myScreen = new SLOGOScreen(this, myStage, myResources, myRoot, language);
		myFrontEndTurtles = new FrontEndTurtles(this);
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
	public void clearTrails() {
		myFrontEndTurtles.getAnimations().add(null);
	}

	@Override
	public void addUserCommand(String userCommand) {
		myUserCommands.add(userCommand);
	}

	@Override
	public void addUserVariable(String userVariable, double value) {
		if (myVariableMap.containsKey(userVariable)){
			variableRemover(userVariable);
		}
		myVariableMap.put(userVariable, value);
		StringBuilder sb2 = new StringBuilder(userVariable);
		sb2.append(" = ");
		sb2.append(value);
		myUserVariables.add(sb2.toString());
	}

	private void variableRemover(String userVariable) {
		Double oldValue = myVariableMap.get(userVariable);
		StringBuilder sb = new StringBuilder(userVariable);
		sb.append(" = ");
		sb.append(oldValue);
		myOldVariables.add(sb.toString());
	}

	@Override
	public void removeUserVariable(String userVariable){
		variableRemover(userVariable);
		myScreen.getDisplaysData().removeVariableFromDisplay(myScreen.getDisplaysData().getVariableDisplay());
	}
	
	@Override
	public void update(Observable o, Object arg) {
		updateScreen();
	}

	@Override
	public void updateScreen(){
		GraphicsContext gc = myFrontEndTurtles.getCanvas().getGraphicsContext2D();
		int numModels = myController.getModels().size();
		while(myFrontEndTurtles.getVisualTurtles().size() < numModels){
			myFrontEndTurtles.setUpTurtle();
		}
		for(int i = 0; i < numModels; i++){
			ImageView myTurtle = myFrontEndTurtles.getVisualTurtles().get(i);
			SLOGOModel myModel = myController.getModels().get(i);
			if (myX.get(i) != myModel.xCor() || myY.get(i) != myModel.yCor()){
				myFrontEndTurtles.setUpPathAnimation(gc, i, myTurtle, myModel);
			}
			if (myHeadings.get(i) != myModel.heading()){
				myFrontEndTurtles.setUpRotateMethod(i, myTurtle, myModel);
			}
			if (myModel.showing() == 0 && myRoot.getChildren().contains(myTurtle)){
				myRoot.getChildren().remove(myTurtle);
			}
			else if (myModel.showing() == 1 && !(myRoot.getChildren().contains(myTurtle))){
				myRoot.getChildren().add(myTurtle);
			}
		}
		if (myFrontEndTurtles.getAnimations().size() > 0)
			myFrontEndTurtles.runAnimation(myFrontEndTurtles.getAnimations().get(myFrontEndTurtles.getCounter()), myFrontEndTurtles.getCounter() + 1);
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

	protected void changeLanguage(String language){
		myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
		myController.changeLanguage();
	}
	
	protected void handleMouseInput(double x, double y){
		int i = 0;
		for (ImageView turtle: myFrontEndTurtles.getVisualTurtles()){
			if (turtle.getBoundsInParent().contains(x, y)){
				myController.toggleActive(i);
			}
			i++;
		}
	}
	
	@Override
	public int getPenColor(){
		return myScreen.getComboBoxesData().getColors().indexOf(myScreen.getComboBoxesData().getPenColorSelector().getValue()) + 1;
	}
	
	@Override
	public int getImage(){
		return myScreen.getComboBoxesData().getImages().indexOf(myScreen.getComboBoxesData().getImageSelector().getValue()) + 1;
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
	public ResourceBundle getResourceBundle() {
		return myResources;
	}
	
	@Override
	public void setPenColor(int index){
		setPenColor(myScreen.getComboBoxesData().getColors().get(index - 1));
		myScreen.getComboBoxesData().getPenColorSelector().setValue(myScreen.getComboBoxesData().getColors().get(index - 1));
	}

	protected void setPenColor(String color){
		myScreen.getComboBoxesData().getPenOptions().setColor(myScreen.getComboBoxesData().getColorsMap().get(color));
	}
	
	@Override
	public void setPenWidth(int index){
		myScreen.getComboBoxesData().getPenOptions().setWidth(index);
		myScreen.getComboBoxesData().getPenWidthSelector().setValue(index);
	}

	@Override
	public void setBackgroundColor(int index){
		myScreen.getTurtleArea().setFill(myScreen.getComboBoxesData().getColorsMap().get(myScreen.getComboBoxesData().getColors().get(index - 1)));
		myScreen.getComboBoxesData().getBackgroundSelector().setValue(myScreen.getComboBoxesData().getColors().get(index - 1));
	}

	@Override
	public void setImage(int index){
		myFrontEndTurtles.changeTurtleImages(myScreen.getComboBoxesData().getImages().get(index - 1));
		myScreen.getComboBoxesData().getImageSelector().setValue(myScreen.getComboBoxesData().getImages().get(index - 1));
	}

	@Override
	public void setController(SLOGOController controller){
		myController = controller;
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
	
	protected Group getRoot(){
		return myRoot;
	}
	
	protected SLOGOScreen getScreen(){
		return myScreen;
	}
	
	protected ArrayList<Double> getX(){
		return myX;
	}
	
	protected ArrayList<Double> getY(){
		return myY;
	}
	
	protected ArrayList<Double> getHeadings(){
		return myHeadings;
	}
	
	protected FrontEndTurtles getFrontEndTurtles(){
		return myFrontEndTurtles;
	}


	@Override
	public int getPenSize() {
		return myScreen.getComboBoxesData().getPenWidthSelector().getValue();
	}


	@Override
	public int getBackground() {
		return myScreen.getComboBoxesData().getColors().indexOf(myScreen.getComboBoxesData().getBackgroundSelector().getValue()) + 1;
	}


	@Override
	public void setLanguage(String language) {
		myScreen.getComboBoxesData().setLanguage(language);
	}
	
	@Override
	public String getLanguage(){
		return myScreen.getComboBoxesData().getLanguage();
	}
}
